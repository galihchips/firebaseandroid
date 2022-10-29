package com.upj.auth

data class MataKuliah (
    val id : String?,
    val mtk : String,
    val sks : Int
) {
    constructor() : this("", "", 0) {

    }
}