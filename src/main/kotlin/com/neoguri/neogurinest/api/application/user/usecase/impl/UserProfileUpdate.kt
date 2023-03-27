package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserProfileUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserProfileUpdateUseCase
import com.neoguri.neogurinest.api.domain.file.entity.File
import com.neoguri.neogurinest.api.domain.file.repository.FileEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.entity.UserFile
import com.neoguri.neogurinest.api.domain.user.enum.UserFileType
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class UserProfileUpdate(
    val userRepository: UserEntityRepositoryInterface,
    val fileRepository: FileEntityRepositoryInterface
) : UserProfileUpdateUseCase {

    @Throws(EntityNotFoundException::class)
    override fun execute(userProfileUpdateDto: UserProfileUpdateDto): UserDto {
        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional(isolation = Isolation.READ_COMMITTED)
            fun(updateDto: UserProfileUpdateDto): UserDto {
                val user: User = userRepository.findByIdOrFail(updateDto.userId)
                val userFile = user.files.filter { it -> it.type === UserFileType.PROFILE }.firstOrNull()

                val profileFileId: String? = updateDto.profileFileId
                if (!isProfileImageUpdateNeeded(profileFileId, userFile)) {
                    user.updateProfile(updateDto, null)
                }

                if (profileFileId !== null) {
                    val file: File = fileRepository.findByIdOrFail(updateDto.profileFileId)
                    user.updateProfile(updateDto, file)
                }

                return UserDto.of(userRepository.save(user))
            }

        return closure(userProfileUpdateDto)
    }

    /**
     * 프로필 이미지 수정이 필요한 경우 체크
     */
    protected fun isProfileImageUpdateNeeded(newFileId: String?, userFile: UserFile?): Boolean {
        // 양 쪽 모두 null 인 경우 배제
        if (newFileId === null && userFile === null) {
            return false
        }

        // 양 쪽 모두 null 이 아니고 기존 값과 같은 경우 제외
        if ((newFileId !== null && userFile !== null)
            && newFileId === userFile.id?.fileId) {
            return false
        }

        return true
    }
}