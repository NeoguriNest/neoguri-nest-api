package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.domain.user.enum.Gender
import com.neoguri.neogurinest.api.domain.user.enum.GenderAttributeConverter
import com.neoguri.neogurinest.api.domain.user.enum.UserStatus
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "users")
open class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    open var id: Int? = null

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

    @Column(name = "address")
    open var address: String? = null

    @Column(name = "address_detail")
    open var addressDetail: String? = null

    @Column(name = "zip_code")
    open var zipCode: String? = null

    @Column(name = "sido")
    open var sido: String? = null

    @Column(name = "sigungu")
    open var sigungu: String? = null

    @Column(name = "eupmyeondong")
    open var eupmyeondong: String? = null

    @Column(name = "gender", nullable = false)
    open var gender: Gender? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: UserStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @OneToMany(fetch = FetchType.LAZY)
    open var agreements: MutableList<UserAgreement>? = null

    @OneToMany(fetch = FetchType.LAZY)
    open var files: MutableList<UserFile>? = null

    @OneToMany(fetch = FetchType.EAGER)
    open var nests: MutableList<UserNest>? = null

    companion object {
        fun create(userAddDto: UserAddDto): User {
            val self = User();
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

    fun updateAddress(addressUpdateDto: UserAddressUpdateDto) {
        this.address = addressUpdateDto.address
        this.addressDetail = addressUpdateDto.addressDetail
        this.zipCode = addressUpdateDto.zipCode
        this.sido = addressUpdateDto.sido
        this.sigungu = addressUpdateDto.sigungu
        this.eupmyeondong = addressUpdateDto.eupmyeondong
    }

}