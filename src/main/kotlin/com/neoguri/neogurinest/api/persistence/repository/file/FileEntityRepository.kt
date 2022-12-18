package com.neoguri.neogurinest.api.persistence.repository.file

import com.neoguri.neogurinest.api.domain.file.entity.File
import com.neoguri.neogurinest.api.domain.file.repository.FileEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.file.repository.jpa.FileRepositoryInterface
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class FileEntityRepository(
    val repository: FileRepositoryInterface
) : FileEntityRepositoryInterface {

    override fun save(entity: File): File {
        return repository.save(entity)
    }

    override fun findById(id: String): File? {
        return repository.findByIdOrNull(id);
    }

    override fun findByIdOrFail(id: String): File {
        return findById(id) ?: throw EntityNotFoundException()
    }
}