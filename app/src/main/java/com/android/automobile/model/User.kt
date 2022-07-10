package com.android.automobile.model

data class User(
    val uid: String? = null,
    val fullName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val type: TYPE = TYPE.ADMIN
)

enum class TYPE {
    CLIENT,
    CUSTOMER,
    ADMIN
}
