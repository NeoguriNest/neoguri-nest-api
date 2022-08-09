package com.neoguri.neogurinest.api.domain.user.repository;

import com.neoguri.neogurinest.api.domain.user.entity.UserNest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserNestRepository : JpaRepository<UserNest, Int> {

}
