package com.neoguri.neogurinest.api.util

import java.util.UUID

object CollectionConverter {

    fun <S> mutableListToArrayList(mutableList: MutableList<S>): List<S> {

        val list = arrayListOf<S>()
        return mutableList.toCollection(list);
    }

}