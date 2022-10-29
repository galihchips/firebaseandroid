package com.upj.auth

data class Catalog (
    val id : String?,
    val title : String,
    val desc : String
) {
    constructor() : this("", "", "") {

    }
}