package com.neoguri.neogurinest.api.domain.nest.entity

import com.neoguri.neogurinest.api.application.nest.dto.request.NestAddDto
import com.neoguri.neogurinest.api.domain.nest.enum.NestStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "nests")
open class Nest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "nest_id", nullable = false)
    open var id: Int? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Column(name = "city", nullable = false)
    open var city: String? = null

    @Column(name = "district", nullable = false)
    open var district: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    open var status: NestStatus? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "last_uploaded_at", nullable = false)
    open var lastUploadedAt: Instant? = null

    companion object {
        fun create(nestAddDto: NestAddDto): Nest {
            val self = Nest()

            self.title = nestAddDto.title
            self.city = nestAddDto.city
            self.district = nestAddDto.district
            self.status = NestStatus.CREATED
            self.createdAt = Instant.now()

            return self
        }
    }
}