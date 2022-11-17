package com.neoguri.neogurinest.api.domain.file.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "files")
open class File {
    @Id
    @Column(name = "file_id", nullable = false, length = 36)
    open var id: String? = null

    @Column(name = "file_extension", nullable = false)
    open var fileExtension: String? = null

    @Column(name = "file_name", nullable = false)
    open var fileName: String? = null

    @Column(name = "file_original_name", nullable = false)
    open var fileOriginalName: String? = null

    @Column(name = "file_url", nullable = false)
    open var fileUrl: String? = null

    @Column(name = "status", nullable = false)
    open var status: String? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null

}