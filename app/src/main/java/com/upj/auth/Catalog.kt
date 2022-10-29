package com.upj.auth

data class Catalog (
    val id : String?,
    val nim : String,
    val name : String
) {
    constructor() : this("", "", "") {

    }
}