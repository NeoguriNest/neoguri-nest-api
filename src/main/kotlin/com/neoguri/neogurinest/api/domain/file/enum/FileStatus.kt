package com.neoguri.neogurinest.api.domain.file.enum

enum class FileStatus(val value: Int) {
    DELETED(-2),
    INVISIBLE(-1),
    VISIBLE(0);
}