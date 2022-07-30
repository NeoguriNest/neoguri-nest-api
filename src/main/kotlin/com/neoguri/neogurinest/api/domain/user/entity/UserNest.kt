package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user_nests")
open class UserNest {
    @EmbeddedId
    open var id: UserNestId? = null

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @MapsId("nestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nest_id", nullable = false)
    open var nest: Nest? = null

    @Column(name = "village", nullable = false)
    open var village: String? = null

    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null
}