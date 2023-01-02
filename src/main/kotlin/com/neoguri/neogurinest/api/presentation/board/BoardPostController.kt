package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostActorDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostAddDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostAddUseCaseInterface
import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/boards/{boardId}")
class BoardPostController(
    val add: BoardPostAddUseCaseInterface,
//    val updateStatus: BoardStatusUpdateUseCaseInterface
) : BaseController() {

    @PostMapping("/posts")
    fun add(
        @RequestBody boardPostAddDto: BoardPostAddDto,
        authentication: AccessTokenAuthentication?
    ): ResponseEntity<BoardPostDto> {
        if (boardPostAddDto.title.trim().isEmpty()) {
            throw BadRequestException("Body/title must be has length over 1")
        }

        if (boardPostAddDto.content.trim().isEmpty()) {
            throw BadRequestException("Body/content must be has length over 1")
        }


        val actor: BoardPostActorDto? =
            if (authentication == null) {
                null
            } else {
                BoardPostActorDto((authentication.details as LoginUserDto).userId)
            }

        return try {
            val boardPost = add.execute(boardPostAddDto, actor)
            ResponseEntity
                .created(URI("/api/boards/${boardPost.boardId}/posts/${boardPost.postId}"))
                .body(boardPost)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

//    @PutMapping("/posts/{postId}/status")
//    fun updateStatus(@RequestBody boardStatusUpdateDto: BoardStatusUpdateDto): ResponseEntity<BoardDto> {
//
//        return try {
//            ResponseEntity.ok(updateStatus.execute(boardStatusUpdateDto))
//        } catch (e: BoardStatusNotConvertableException) {
//            throw BadRequestException(e.message!!)
//        } catch (e: StatusAlreadyChangedException) {
//            throw ConflictException(e.message!!)
//        }
//    }
}