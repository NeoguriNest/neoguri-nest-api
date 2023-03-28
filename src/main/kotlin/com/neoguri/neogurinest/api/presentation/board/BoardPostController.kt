package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.board.post.dto.*
import com.neoguri.neogurinest.api.application.board.post.usecase.*
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationRequestDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.common.CursorStringParser
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.*
import com.neoguri.neogurinest.api.presentation.param.OrderDtoBuilder
import com.neoguri.neogurinest.api.util.Decoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/board/posts")
class BoardPostController(
    val add: BoardPostAddUseCase,
    val get: BoardPostGetUseCase,
    val getManyUsingCursorPagination: BoardPostGetManyUsingCursorUseCase,
    val cursorStringParser: CursorStringParser,
    val update: BoardPostUpdateUseCase,
    val statusUpdate: BoardPostStatusUpdateUseCase
) : BaseController() {

    @GetMapping("{postId}")
    fun get(
        @PathVariable("postId") postId: String,
    ): ResponseEntity<BoardPostDto> {
        val boardContext = BoardContext.getInstance()
        return try {
            val payload = get.execute(postId, boardContext.actor)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(postId)
        }
    }

    @GetMapping
    fun getManyUsingCursorPagination(
        @ModelAttribute filter: BoardPostFilterDto,
        @ModelAttribute cursorPaginationRequest: CursorPaginationRequestDto,
    ): ResponseEntity<CursorPaginatedResultDto<BoardPostDto>> {
        val boardContext = BoardContext.getInstance()
        val cursorPaginationDto = CursorPaginationDto(
            cursorStringParser.parse(cursorPaginationRequest.cursor?.let { Decoder decodes it }),
            cursorPaginationRequest.size
        )

        val orders = OrderDtoBuilder(cursorPaginationRequest.order).build()

        return try {
            val payload = getManyUsingCursorPagination.execute(filter, cursorPaginationDto, orders, boardContext.actor)
            ResponseEntity
                .ok()
                .body(payload)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

    @PostMapping
    fun add(
        @RequestBody boardPostAddDto: BoardPostAddDto,
    ): ResponseEntity<BoardPostDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        if (boardPostAddDto.title.trim().isEmpty()) {
            throw BadRequestException("Body/title must be has length over 1")
        }

        if (boardPostAddDto.content.trim().isEmpty()) {
            throw BadRequestException("Body/content must be has length over 1")
        }


        return try {
            val boardPost = add.execute(boardPostAddDto, boardContext.actor)
            ResponseEntity
                .created(URI("/api/board/posts/${boardPost.postId}"))
                .body(boardPost)
        } catch (e: BoardChannelNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw ForbiddenException(e.message!!)
        }
    }

    @PutMapping("/{postId}")
    fun update(@RequestBody boardPostUpdateDto: BoardPostUpdateDto): ResponseEntity<BoardPostDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            ResponseEntity.ok(update.execute(boardPostUpdateDto, boardContext.actor!!))
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw BadRequestException(e.message!!)
        }
    }

    @PatchMapping("/{postId}/status")
    fun updateStatus(@RequestBody boardPostStatusUpdateDto: BoardPostStatusUpdateDto): ResponseEntity<BoardPostDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            ResponseEntity.ok(statusUpdate.execute(boardPostStatusUpdateDto, boardContext.actor!!))
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw BadRequestException(e.message!!)
        }
    }

}