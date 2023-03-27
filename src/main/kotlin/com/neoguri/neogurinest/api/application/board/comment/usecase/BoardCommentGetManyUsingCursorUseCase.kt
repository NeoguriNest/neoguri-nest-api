package com.neoguri.neogurinest.api.application.board.comment.usecase

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.application.board.usecase.GetManyUsingCursorUseCase

interface BoardCommentGetManyUsingCursorUseCase : GetManyUsingCursorUseCase<BoardCommentFilterDto, BoardCommentDto>