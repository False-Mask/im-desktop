package screen.model

import bean.Contacts
import bean.IM
import bean.MessageItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import screen.repo.MainRepo
import utils.scope

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  18:55
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */

//客户端连接状态
sealed class ConnectState {
    object Idle : ConnectState()
    data class Connected(val webSocket: WebSocket, val response: Response) : ConnectState()

    data class Failed(val throwable: Throwable, val response: Response?) : ConnectState()
    object Closed : ConnectState()

}

class MainModel {

    private val _listData: MutableStateFlow<List<Contacts>> = MutableStateFlow(listOf())
    val listData: StateFlow<List<Contacts>> = _listData

    fun searchFriends(id: Int) {
        scope.launch {
            _listData.emit(
                MainRepo.getFriends(id)
                    .data
                    .map {
                        Contacts(
                            name = it.name,
                            lastMessage = it.lastMessage,
                            lastTime = it.lastTime,
                            icon = it.profile,
                            uid = it.uid,
                        )
                    }.sortedByDescending {
                        it.lastTime
                    }
            )
        }
    }

    private val _chat: MutableStateFlow<List<MessageItem>> = MutableStateFlow(listOf())
    val chat: StateFlow<List<MessageItem>> = _chat

    fun chat(from: Int, to: Int) {
        println(this.hashCode())
        scope.launch {
            _chat.emit(MainRepo.chaList(from, to)
                .data
                .map {
                    MessageItem(it.selfMessage, it.profile, it.message, it.timeMills)
                }.sortedBy {
                    it.timeMills
                })
        }
    }

    fun post(im: IM) {
        val value = _chat.value
        val msg = im.data.msg
        val sender = im.data.from
        val l = ArrayList(value)
        l.add(MessageItem(false, sender.profile, msg.content, msg.time))
        _chat.value = l
    }


    private val _wsConnect = MutableStateFlow<ConnectState>(ConnectState.Idle)
    val wsConnect: StateFlow<ConnectState> = _wsConnect

    fun updateWsState(state: ConnectState) {
        scope.launch {
            _wsConnect.emit(state)
        }
    }

    private val _currentContact = MutableStateFlow<Contacts>(Contacts())
    val currentContact: StateFlow<Contacts> = _currentContact

    fun updateContact(contacts: Contacts) {
        scope.launch {
            _currentContact.emit(contacts)
        }
    }

    private val _clearEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val clearEvent: SharedFlow<Unit> = _clearEvent

    fun clear() {
        scope.launch {
            _clearEvent.emit(Unit)
        }
    }

}