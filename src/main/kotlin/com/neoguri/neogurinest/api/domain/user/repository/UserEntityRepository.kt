package com.neoguri.neogurinest.api.domain.user.repository

import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.persistence.repository.user.UserAgreementRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserFileRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserNestRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserRepository
import com.neoguri.neogurinest.api.util.CollectionConverter
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityNotFoundException

@Repository
class UserEntityRepository(
    val userRepository: UserRepository,
    val userFileRepository: UserFileRepository,
    val userAgreementRepository: UserAgreementRepository,
    val userNestRepository: UserNestRepository
) : UserEntityRepositoryInterface {

    override fun save(entity: User): User {
        var user: User?
        try {
            user = userRepository.save(entity)
            if (user.files !== null) {
                userFileRepository.saveAll(CollectionConverter.mutableListToArrayList(user.files!!))
            }
            if (user.nests !== null) {
                userNestRepository.saveAll(CollectionConverter.mutableListToArrayList(user.nests!!))
            }
            if (user.agreements !== null) {
                userAgreementRepository.saveAll(CollectionConverter.mutableListToArrayList(user.agreements!!))
            }
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw DuplicatedEntityException();
        }

        return user
    }

    override fun findById(id: Int): User? {
        val user: Optional<User> = userRepository.findById(id);

        return user.get();
    }

    override fun findByIdOrFail(id: Int): User {
        val user: User? = this.findById(id);
        if (user === null) {
            throw EntityNotFoundException();
        }

        return user;
    }

}