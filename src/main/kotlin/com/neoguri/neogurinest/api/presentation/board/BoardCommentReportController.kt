package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportAddDto
import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportDto
import com.neoguri.neogurinest.api.application.board.action.comment.usecase.BoardCommentReportAddUseCase
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.ForbiddenException
import com.neoguri.neogurinest.api.presentation.exception.NotFoundException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/board/comment-reports")
class BoardCommentReportController(
    val add: BoardCommentReportAddUseCase
) : BaseController() {

    @PostMapping
    fun add(
        @RequestBody addDto: BoardCommentReportAddDto,
    ): ResponseEntity<BoardCommentReportDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            val payload = add.execute(addDto, boardContext.actor!!)
            ResponseEntity
                .created(URI("/api/board/comment-reports/${payload.reportId}"))
                .body(payload)
        } catch (e: BoardChannelNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw ForbiddenException(e.message!!)
        }
    }
}