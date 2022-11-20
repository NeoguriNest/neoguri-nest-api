package com.neoguri.neogurinest.api.domain.nest.repository.jpa

import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NestRepositoryInterface : JpaRepository<Nest, Int> {

}
