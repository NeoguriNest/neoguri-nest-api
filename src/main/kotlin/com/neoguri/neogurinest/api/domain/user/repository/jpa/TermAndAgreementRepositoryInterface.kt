package com.neoguri.neogurinest.api.domain.user.repository.jpa;

import com.neoguri.neogurinest.api.domain.user.entity.TermsAndAgreement
import com.neoguri.neogurinest.api.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermAndAgreementRepositoryInterface : JpaRepository<TermsAndAgreement, Int> {

}
