package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentAddDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentAddUseCase
import com.neoguri.neogurinest.api.application.board.comment.usecase.impl.BoardCommentAdd
import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.*
import com.neoguri.neogurinest.api.application.board.post.usecase.*
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationRequestDto
import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotCommentableException
import com.neoguri.neogurinest.api.domain.common.CursorStringParser
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.*
import com.neoguri.neogurinest.api.presentation.param.OrderDtoBuilder
import com.neoguri.neogurinest.api.util.Decoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.Forbidden
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import java.net.URI

@RestController
@RequestMapping("/api/board/comments")
class BoardCommentController(
    val add: BoardCommentAddUseCase
) : BaseController() {

    @PostMapping
    fun add(
        @RequestBody addDto: BoardCommentAddDto,
        authentication: AccessTokenAuthentication?
    ): ResponseEntity<BoardCommentDto> {

        val actor: BoardActor =
            if (authentication == null) {
                throw UnauthorizedException("You must be authorized for this api")
            } else {
                BoardActor((authentication.details as LoginUserDto).userId)
            }

        return try {
            val payload = add.execute(addDto, actor)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: BoardPostStatusNotCommentableException) {
            throw ForbiddenException("댓글을 작성할 수 없는 게시글입니다.")
        }
    }

}