package com.neoguri.neogurinest.api.persistence.repository.nest

import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.nest.repository.NestEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.nest.repository.jpa.NestRepositoryInterface
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class NestEntityRepository(
    val nestRepository: NestRepositoryInterface
) : NestEntityRepositoryInterface {

    override fun save(entity: Nest): Nest {
        nestRepository.save(entity)

        return entity
    }

    override fun findById(id: Int): Nest? {
        return nestRepository.findByIdOrNull(id)
    }

    override fun findByIdOrFail(id: Int): Nest {
        val nest: Nest? = this.findById(id)
        if (nest === null) {
            throw EntityNotFoundException()
        }

        return nest
    }

}