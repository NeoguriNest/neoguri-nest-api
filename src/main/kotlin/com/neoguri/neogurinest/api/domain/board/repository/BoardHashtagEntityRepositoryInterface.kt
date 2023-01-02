package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardHashtagEntityRepositoryInterface: AggregateRootRepository<BoardHashtag, String> {

    fun findByNameIn(names: List<String>): List<BoardHashtag>

    fun saveAll(entities: List<BoardHashtag>): List<BoardHashtag>
}