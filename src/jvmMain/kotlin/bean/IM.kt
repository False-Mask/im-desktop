package bean


import com.google.gson.annotations.SerializedName

data class IM(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("from")
        val from: From,
        @SerializedName("msg")
        val msg: Msg
    ) {
        data class From(
            @SerializedName("age")
            val age: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("profile")
            val profile: String,
            @SerializedName("sex")
            val sex: String,
            @SerializedName("uid")
            val uid: Int,
            @SerializedName("userName")
            val userName: String
        )

        data class Msg(
            @SerializedName("content")
            val content: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("msgFrom")
            val msgFrom: Int,
            @SerializedName("msgTo")
            val msgTo: Int,
            @SerializedName("time")
            val time: Long
        )
    }
}