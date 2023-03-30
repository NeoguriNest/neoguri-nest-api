package com.neoguri.neogurinest.api.presentation.board

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostBookmarkAddUseCase
import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostLikeAddUseCase
import com.neoguri.neogurinest.api.application.board.post.dto.*
import com.neoguri.neogurinest.api.application.board.post.usecase.*
import com.neoguri.neogurinest.api.application.common.dto.*
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.ModifyingOtherUsersPostException
import com.neoguri.neogurinest.api.domain.common.CursorStringParser
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.BadRequestException
import com.neoguri.neogurinest.api.presentation.exception.ForbiddenException
import com.neoguri.neogurinest.api.presentation.exception.NotFoundException
import com.neoguri.neogurinest.api.presentation.exception.UnauthorizedException
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
    val getManyUsingPagination: BoardPostGetManyUsingPaginationUseCase,
    val getManyUsingCursor: BoardPostGetManyUsingCursorUseCase,
    val update: BoardPostUpdateUseCase,
    val statusUpdate: BoardPostStatusUpdateUseCase,
    val likeAdd: BoardPostLikeAddUseCase,
    val bookmarkAdd: BoardPostBookmarkAddUseCase,
    val cursorStringParser: CursorStringParser
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


    @GetMapping("/paginated")
    fun getManyUsingPagination(
        @ModelAttribute filter: BoardPostFilterDto,
        @ModelAttribute pagination: PaginationRequestDto
    ): ResponseEntity<PaginatedResultDto<BoardPostDto>> {
        val boardContext = BoardContext.getInstance()

        val payload = getManyUsingPagination.execute(
            filter,
            PaginationDto.of(pagination),
            boardContext.actor
        )
        return ResponseEntity.ok().body(payload)
    }


    @GetMapping("/cursor-paginated")
    fun getManyUsingCursorPagination(
        @ModelAttribute filter: BoardPostFilterDto,
        @ModelAttribute paginationRequest: CursorPaginationRequestDto,
    ): ResponseEntity<CursorPaginatedResultDto<BoardPostDto>> {
        val boardContext = BoardContext.getInstance()
        val pagination = CursorPaginationDto(
            cursorStringParser.parse(paginationRequest.cursor?.let { Decoder decodes it }),
            paginationRequest.size
        )
        val payload = getManyUsingCursor.execute(
            filter,
            pagination,
            OrderDtoBuilder(paginationRequest.order).build(),
            boardContext.actor
        )
        return ResponseEntity
            .ok()
            .body(payload)
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
        } catch (e: ModifyingOtherUsersPostException) {
            throw ForbiddenException(e.message!!)
        }
    }

    @PatchMapping("/{postId}/status")
    fun updateStatus(
        @PathVariable("postId") postId: String,
        @RequestBody boardPostStatusUpdateDto: BoardPostStatusUpdateDto
    ): ResponseEntity<BoardPostDto> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            ResponseEntity.ok(statusUpdate.execute(postId, boardPostStatusUpdateDto, boardContext.actor!!))
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw BadRequestException(e.message!!)
        } catch (e: ModifyingOtherUsersPostException) {
            throw ForbiddenException(e.message!!)
        }
    }

    @PostMapping("/{postId}/likes")
    fun like(
        @PathVariable("postId") postId: String
    ): ResponseEntity<Boolean> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            ResponseEntity.ok(likeAdd.execute(postId, boardContext.actor!!))
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw BadRequestException(e.message!!)
        } catch (e: ModifyingOtherUsersPostException) {
            throw ForbiddenException(e.message!!)
        }
    }

    @PostMapping("/{postId}/bookmarks")
    fun bookmark(
        @PathVariable("postId") postId: String
    ): ResponseEntity<Boolean> {
        val boardContext = BoardContext.getInstance()
        if (boardContext.isAnonymous()) {
            throw UnauthorizedException("You must be authorized for this api")
        }

        return try {
            ResponseEntity.ok(bookmarkAdd.execute(postId, boardContext.actor!!))
        } catch (e: BoardPostNotFoundException) {
            throw NotFoundException(e.message!!)
        } catch (e: BoardChannelNotAvailableStatusException) {
            throw BadRequestException(e.message!!)
        } catch (e: ModifyingOtherUsersPostException) {
            throw ForbiddenException(e.message!!)
        }
    }
}