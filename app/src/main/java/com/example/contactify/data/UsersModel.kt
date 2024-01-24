package com.example.contactify.data

import java.io.Serializable


//Response DataClass
data class UsersModel(
    val page: Int?,
    val per_page: Int?,
    val total: Int?,
    val total_pages: Int?,
    val data: ArrayList<Data>?,
    val support: Support?
) : Serializable

data class Data(
    val id: Int?,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val avatar: String?
) : Serializable

data class Support(
    val url: String?,
    val text: String?
) : Serializable