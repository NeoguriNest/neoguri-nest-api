package com.neoguri.neogurinest.api.persistence.repository.user

import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.repository.jpa.UserAgreementRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.repository.jpa.UserFileRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.repository.jpa.UserNestRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.repository.jpa.UserRepositoryInterface
import org.springframework.data.domain.Example
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityNotFoundException

@Repository
class UserEntityRepository(
    val userRepository: UserRepositoryInterface,
    val userFileRepository: UserFileRepositoryInterface,
    val userAgreementRepository: UserAgreementRepositoryInterface,
    val userNestRepository: UserNestRepositoryInterface
) : UserEntityRepositoryInterface {

    override fun save(entity: User): User {
        userRepository.save(entity)
        userFileRepository.saveAll(entity.files.toMutableList())
        userNestRepository.saveAll(entity.nests.toMutableList())
        userAgreementRepository.saveAll(entity.agreements.toMutableList())

        return entity
    }

    override fun checkExistsByEmail(email: String): Boolean {
        val example = User()
        example.email = email

        return userRepository.exists(Example.of(example))
    }

    override fun findById(id: Int): User? {
        return userRepository.findByIdOrNull(id)
    }

    override fun findByIdOrFail(id: Int): User {
        return findById(id) ?: throw EntityNotFoundException()
    }

    override fun findByEmail(email: String): Optional<User> {
        val example = User()
        example.email = email

        return userRepository.findOne(Example.of(example))
    }

    override fun findByEmailOrFail(email: String): User {
        val user: Optional<User> = this.findByEmail(email)
        if (user.isEmpty) {
            throw EntityNotFoundException()
        }

        return user.get()
    }

}