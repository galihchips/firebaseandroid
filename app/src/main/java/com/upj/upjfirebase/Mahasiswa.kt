package com.upj.upjfirebase

data class Mahasiswa (
    val id : String?,
    val nim : String,
    val name : String
) {
    constructor() : this("", "", "") {

    }
}