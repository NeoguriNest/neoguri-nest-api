package com.neoguri.neogurinest.api.domain.user.repository.jpa;

import com.neoguri.neogurinest.api.domain.user.entity.UserAgreement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAgreementRepositoryInterface : JpaRepository<UserAgreement, Int> {

}
