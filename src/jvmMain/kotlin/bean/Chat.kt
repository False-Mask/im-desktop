package bean


import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("message")
        val message: String,
        @SerializedName("profile")
        val profile: String,
        @SerializedName("selfMessage")
        val selfMessage: Boolean,
        @SerializedName("timeMills")
        val timeMills: Long
    )
}