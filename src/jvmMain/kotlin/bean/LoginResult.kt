package bean

data class LoginResult(
    val code: Int = -1,
    val msg: String = "",
    val data: String = "",
)