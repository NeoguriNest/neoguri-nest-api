package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.board.usecase.GetManyUsingPaginationUseCase

interface BoardPostGetManyUsingPaginationUseCase: GetManyUsingPaginationUseCase<BoardPostFilterDto, BoardPostDto>