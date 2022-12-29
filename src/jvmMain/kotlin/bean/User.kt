package bean


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("age")
    val age: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("pwd")
    val pwd: String,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("userName")
    val userName: String
)