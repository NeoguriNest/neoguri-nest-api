package com.neoguri.neogurinest.api.domain.common

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

data class CursorPageRequest<T>(
    val cursors: List<Cursor>,
    val specification: Specification<T>?,
    val page: Pageable
) {}
