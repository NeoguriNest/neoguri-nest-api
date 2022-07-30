package com.neoguri.neogurinest.api.domain.nest.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "nests")
open class Nest {
    @Id
    @Column(name = "nest_id", nullable = false)
    open var id: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Column(name = "city", nullable = false)
    open var city: String? = null

    @Column(name = "district", nullable = false)
    open var district: String? = null

    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "last_uploaded_at", nullable = false)
    open var lastUploadedAt: Instant? = null
}