package bean

import com.google.gson.annotations.SerializedName

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  20:28
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
data class Message(
    val id: Int = -1,
    @SerializedName("msgFrom")
    val msgFrom: Int = -1,
    @SerializedName("msgTo")
    val msgTo: Int = -1,
    @SerializedName("content")
    val content: String = "",
    val time: Long = System.currentTimeMillis()
)