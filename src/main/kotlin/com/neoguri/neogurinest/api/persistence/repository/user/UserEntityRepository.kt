package com.neoguri.neogurinest.api.persistence.repository.user

import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.*
import com.neoguri.neogurinest.api.util.CollectionConverter
import org.springframework.data.domain.Example
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
        userRepository.save(entity)
        if (entity.files !== null) {
            userFileRepository.saveAll(CollectionConverter.mutableListToArrayList(entity.files!!))
        }
        if (entity.nests !== null) {
            userNestRepository.saveAll(CollectionConverter.mutableListToArrayList(entity.nests!!))
        }
        if (entity.agreements !== null) {
            userAgreementRepository.saveAll(CollectionConverter.mutableListToArrayList(entity.agreements!!))
        }

        return entity
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

    override fun findByLoginId(loginId: String): User? {
        val example = User()
        example.loginId = loginId;

        val user: Optional<User> = userRepository.findOne(Example.of(example));

        return user.get();
    }

}