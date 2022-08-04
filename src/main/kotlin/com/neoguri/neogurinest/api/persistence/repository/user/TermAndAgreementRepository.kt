package com.neoguri.neogurinest.api.persistence.repository.user;

import com.neoguri.neogurinest.api.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermAndAgreementRepository : JpaRepository<User, Int> {

}
