package bean


import com.google.gson.annotations.SerializedName

data class MessageToSend(
    @SerializedName("content")
    val content: String,
    @SerializedName("msgFrom")
    val msgFrom: Int,
    @SerializedName("msgTo")
    val msgTo: Int
)