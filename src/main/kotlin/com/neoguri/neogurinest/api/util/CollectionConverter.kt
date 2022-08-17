package com.neoguri.neogurinest.api.util

object CollectionConverter {

    fun <S> mutableListToArrayList(mutableList: MutableList<S>): List<S> {

        val list = arrayListOf<S>()
        return mutableList.toCollection(list);
    }

}