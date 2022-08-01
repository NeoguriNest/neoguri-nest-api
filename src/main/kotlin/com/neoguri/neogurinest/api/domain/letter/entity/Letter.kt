package com.neoguri.neogurinest.api.domain.letter.entity

import com.neoguri.neogurinest.api.domain.letter.enum.LetterStatus
import com.neoguri.neogurinest.api.domain.user.entity.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "letters")
open class Letter {
    @Id
    @Column(name = "letter_id", nullable = false, length = 36)
    open var id: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    open var sender: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    open var receiver: User? = null

    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: LetterStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "receiver_read_at", nullable = false)
    open var receiverReadAt: Instant? = null
}