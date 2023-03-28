package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentAddDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentUpdateDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.*
import com.neoguri.neogurinest.api.application.common.dto.*
import com.neoguri.neogurinest.api.domain.aspect.NoInjection
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.domain.board.exception.*
import com.neoguri.neogurinest.api.domain.common.CursorStringParser
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import com.neoguri.neogurinest.api.domain.common.type.StringToTypeConverter
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import com.neoguri.neogurinest.api.presentation.exception.ForbiddenException
import com.neoguri.neogurinest.api.presentation.exception.NotFoundException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
import com.neoguri.neogurinest.api.presentation.param.OrderDtoBuilder
import com.neoguri.neogurinest.api.util.Decoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/board/comments")
class BoardCommentController(
    val add: BoardCommentAddUseCase,
    val update: BoardCommentUpdateUseCase,
    val statusUpdate: BoardCommentStatusUpdateUseCase,
    val getManyUsingPagination: BoardCommentGetManyUsingPaginationUseCase,
    val getManyUsingCursor: BoardCommentGetManyUsingCursorUseCase,
    val converter: StringToTypeConverter
) : BaseController() {

    @PostMapping
    fun add(
        @RequestBody addDto: BoardCommentAddDto
    ): ResponseEntity<BoardCommentDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }
        return try {
            val payload = add.execute(addDto, boardContext.actor!!)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException("게시글을 찾을 수 없습니다.")
        } catch (e: BoardPostStatusNotCommentableException) {
            throw ForbiddenException("댓글을 작성할 수 없는 게시글입니다.")
        }
    }

    @PutMapping("/{commentId}")
    fun update(
        @PathVariable("commentId") commentId: String,
        @RequestBody updateDto: BoardCommentUpdateDto
    ): ResponseEntity<BoardCommentDto> {

        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }
        return try {
            val payload = update.execute(commentId, updateDto, boardContext.actor!!)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException("게시글을 찾을 수 없습니다.")
        } catch (e: ModifyingOtherUsersCommentException) {
            throw ForbiddenException("댓글을 수정할 권한이 없습니다.")
        } catch (e: BoardPostStatusNotCommentableException) {
            throw ForbiddenException("댓글을 작성할 수 없는 게시글입니다.")
        }
    }

    @PatchMapping("/{commentId}/block")
    fun block(
        @PathVariable("commentId") commentId: String,
        @RequestBody updateDto: BoardCommentUpdateDto
    ): ResponseEntity<Boolean> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }
        return try {
            val payload = statusUpdate.execute(commentId, BoardCommentStatus.BLOCKED, boardContext.actor!!)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: StatusAlreadyChangedException) {
            throw ConflictException("차단된 댓글입니다.")
        } catch (e: BoardCommentNotFoundException) {
            throw NotFoundException("댓글을 찾을 수 없습니다.")
        } catch (e: BoardCommentStatusNotConvertableException) {
            throw ForbiddenException("댓글의 상태를 변경할 수 없습니다.")
        }
    }

    @DeleteMapping("/{commentId}")
    fun delete(
        @PathVariable("commentId") commentId: String,
        @RequestBody updateDto: BoardCommentUpdateDto
    ): ResponseEntity<Boolean> {

        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            val payload = statusUpdate.execute(commentId, BoardCommentStatus.DELETED, boardContext.actor!!)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: StatusAlreadyChangedException) {
            throw ConflictException("삭제된 댓글입니다.")
        } catch (e: BoardCommentNotFoundException) {
            throw NotFoundException("댓글을 찾을 수 없습니다.")
        } catch (e: BoardCommentStatusNotConvertableException) {
            throw ForbiddenException("댓글의 상태를 변경할 수 없습니다.")
        }
    }

    @GetMapping("/paginated")
    fun getManyUsingPagination(
        @ModelAttribute filter: BoardCommentFilterDto,
        @ModelAttribute pagination: PaginationRequestDto
    ): ResponseEntity<PaginatedResultDto<BoardCommentDto>> {
        val boardContext = BoardContext.getInstance()
        val payload = getManyUsingPagination.execute(filter, PaginationDto.of(pagination), boardContext.actor)
        return ResponseEntity
            .ok()
            .body(payload)
    }

    @GetMapping("/cursor-paginated")
    fun getManyUsingCursor(
        @ModelAttribute filter: BoardCommentFilterDto,
        @ModelAttribute pagination: CursorPaginationRequestDto
    ): ResponseEntity<CursorPaginatedResultDto<BoardCommentDto>> {
        val boardContext = BoardContext.getInstance()
        val cursors = CursorStringParser(converter).parse(pagination.cursor?.let { Decoder decodes it })
        val orders = OrderDtoBuilder(pagination.order).build()

        val payload = getManyUsingCursor.execute(filter, CursorPaginationDto(cursors, pagination.size), orders, boardContext.actor)
        return ResponseEntity
            .ok()
            .body(payload)
    }

}