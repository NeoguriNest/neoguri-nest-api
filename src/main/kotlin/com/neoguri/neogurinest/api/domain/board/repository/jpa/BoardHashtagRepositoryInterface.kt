package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import org.springframework.data.jpa.repository.JpaRepository

interface BoardHashtagRepositoryInterface : JpaRepository<BoardHashtag, String> {
    fun findByNameIn(names: List<String>): List<BoardHashtag>
}