package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.application.user.dto.request.UserNestAddDto
import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.user.enum.UserNestStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user_nests")
open class UserNest {
    @EmbeddedId
    open var id: UserNestId? = null

    @MapsId("userId")
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @MapsId("nestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nest_id", nullable = false)
    open var nest: Nest? = null

    @Column(name = "village", nullable = false)
    open var village: String? = null

    @Column(name = "status", nullable = false)
    open var status: UserNestStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    companion object {
        fun create(addDto: UserNestAddDto): UserNest {
            val userNest = UserNest()

            userNest.id = UserNestId.of(addDto.userId, addDto.nestId)
            userNest.village = addDto.village
            userNest.status = UserNestStatus.JOINED
            userNest.createdAt = Instant.now()

            return userNest
        }
    }
}