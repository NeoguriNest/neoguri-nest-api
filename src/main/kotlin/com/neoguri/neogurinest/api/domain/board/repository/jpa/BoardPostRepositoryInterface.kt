package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BoardPostRepositoryInterface : JpaRepository<BoardPost, String>, JpaSpecificationExecutor<BoardPost> {}