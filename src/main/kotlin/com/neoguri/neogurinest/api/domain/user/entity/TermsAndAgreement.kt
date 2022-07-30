package com.neoguri.neogurinest.api.domain.user.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "terms_and_agreements")
open class TermsAndAgreement {
    @Id
    @Column(name = "term_id", nullable = false)
    open var id: Int? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

    @Lob
    @Column(name = "type", nullable = false)
    open var type: String? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}