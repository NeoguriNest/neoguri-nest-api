package com.neoguri.neogurinest.api.domain.user.entity

import com.neoguri.neogurinest.api.application.user.dto.request.UserFileAddDto
import com.neoguri.neogurinest.api.domain.file.entity.File
import com.neoguri.neogurinest.api.domain.user.enum.UserFileType
import javax.persistence.*

@Entity
@Table(name = "user_files")
open class UserFile {
    @EmbeddedId
    open var id: UserFileId? = null

    @Column(name = "file_url", nullable = false)
    open var fileUrl: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    open var type: UserFileType? = null

    companion object {
        fun create(user: User, file: File, type: UserFileType): UserFile {
            val userFile = UserFile()

            val id = UserFileId()
            id.userId = user.id
            id.fileId = file.id

            userFile.id = id
            userFile.fileUrl = file.fileUrl
            userFile.type = type
            return userFile
        }

    }
}