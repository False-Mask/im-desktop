package bean

/**
 *@author ZhiQiang Tu
 *@time 2022/12/27  10:10
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
data class Contacts(
    val uid: Int = -1,
    val name: String = "",
    val lastTime: Long = 0,
    val icon: String = "",
    val lastMessage: String = "",
    val type: ContactsType = ContactsType.PERSON,
)

enum class ContactsType {
    GROUP, PERSON
}