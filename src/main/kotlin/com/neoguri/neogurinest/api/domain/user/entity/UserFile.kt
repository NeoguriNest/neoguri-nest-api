package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.domain.user.enum.UserFileType
import javax.persistence.*

@Entity
@Table(name = "user_files")
open class UserFile {
    @EmbeddedId
    open var id: UserFileId? = null

    @Column(name = "file_url", nullable = false)
    open var fileUrl: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    open var type: UserFileType? = null
}