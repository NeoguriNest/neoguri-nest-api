package com.neoguri.neogurinest.api.application.board.post.dto

data class BoardPostAddDto(
    val boardId: String,
    val title: String,
    val content: String,
    val hashTags: List<String>
) {}