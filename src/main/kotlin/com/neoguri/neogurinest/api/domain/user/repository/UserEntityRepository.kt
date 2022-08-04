package com.neoguri.neogurinest.api.domain.user.repository

import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.persistence.repository.user.UserAgreementRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserFileRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserNestRepository
import com.neoguri.neogurinest.api.persistence.repository.user.UserRepository
import com.neoguri.neogurinest.api.util.CollectionConverter
import org.springframework.stereotype.Repository

@Repository
class UserEntityRepository(
    val userRepository: UserRepository,
    val userFileRepository: UserFileRepository,
    val userAgreementRepository: UserAgreementRepository,
    val userNestRepository: UserNestRepository
) : UserEntityRepositoryInterface {

    override fun save(entity: User): User {
        val user: User = userRepository.save(entity)

        if (user.files !== null) {
            userFileRepository.saveAll(CollectionConverter.mutableListToArrayList(user.files!!))
        }
        if (user.nests !== null) {
            userNestRepository.saveAll(CollectionConverter.mutableListToArrayList(user.nests!!))
        }
        if (user.agreements !== null) {
            userAgreementRepository.saveAll(CollectionConverter.mutableListToArrayList(user.agreements!!))
        }

        return user
    }

}