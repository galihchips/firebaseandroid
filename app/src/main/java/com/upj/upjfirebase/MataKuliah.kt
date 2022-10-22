package com.upj.upjfirebase

data class MataKuliah (
    val id : String?,
    val mtk : String,
    val sks : Int
) {
    constructor() : this("", "", 0) {

    }
}