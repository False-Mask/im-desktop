package screen.model

import bean.Contacts
import bean.ContactsType
import bean.MessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import screen.repo.MainRepo
import utils.scope
import java.text.DateFormat
import java.util.Date

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  18:55
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
class MainModel {

    private val _listData: MutableStateFlow<List<Contacts>> = MutableStateFlow(listOf())
    val listData: StateFlow<List<Contacts>> = _listData

    private val _chat: MutableStateFlow<List<MessageItem>> = MutableStateFlow(listOf())
    val chat: StateFlow<List<MessageItem>> = _chat
    fun searchFriends(id: Int) {
        scope.launch {
            _listData.value = MainRepo.getFriends(id)
                .data
                .map {
                    Contacts(
                        name = it.name,
                        lastMessage = it.lastMessage,
                        lastTime = it.lastTime,
                        icon = it.profile,
                        uid = it.uid,
                    )
                }
        }
    }

    fun chat(from: Int, to: Int) {
        scope.launch {
            _chat.value = MainRepo.chaList(from,to)
                .data
                .map {
                    MessageItem(it.selfMessage,it.profile,it.message,it.timeMills)
                }
        }
    }

}