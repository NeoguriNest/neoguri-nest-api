package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.application.user.dto.UserAddDto
import com.neoguri.neogurinest.api.domain.user.enum.Gender
import com.neoguri.neogurinest.api.domain.user.enum.UserStatus
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "users")
open class User {
    @GeneratedValue
    @Id
    @Column(name = "user_id", nullable = false)
    open var id: Int? = null

    @Column(name = "login_id", nullable = false)
    open var loginId: String? = null

    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "nickname", nullable = false)
    open var nickname: String? = null

    @Column(name = "birthdate", nullable = false)
    open var birthdate: String? = null

    @Column(name = "introduction_text", nullable = false)
    open var introductionText: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    open var gender: Gender? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: UserStatus? = null

    @OneToMany(fetch = FetchType.LAZY)
    open var agreements: MutableList<UserAgreement>? = null

    @OneToMany(fetch = FetchType.LAZY)
    open var files: MutableList<UserFile>? = null

    @OneToMany
    open var nests: MutableList<UserNest>? = null

    companion object {
        fun create(userAddDto: UserAddDto): User {
            val self = User();
            self.loginId = userAddDto.loginId
            self.password = PasswordEncryptor.encrypt(userAddDto.password)
            self.nickname = userAddDto.nickname
            self.email = userAddDto.email
            self.gender = userAddDto.gender
            self.birthdate = userAddDto.birthdate
            self.status = UserStatus.ACTIVATED
            self.createdAt = Instant.now()
            self.updatedAt = Instant.now()
            self.introductionText = userAddDto.introductionText

            return self
        }
    }

}