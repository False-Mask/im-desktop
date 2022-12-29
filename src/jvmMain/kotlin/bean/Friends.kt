package bean


import com.google.gson.annotations.SerializedName

data class Friends(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("age")
        val age: Int,
        @SerializedName("lastMessage")
        val lastMessage: String,
        @SerializedName("lastTime")
        val lastTime: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("profile")
        val profile: String,
        @SerializedName("sex")
        val sex: String,
        @SerializedName("uid")
        val uid: Int
    )
}