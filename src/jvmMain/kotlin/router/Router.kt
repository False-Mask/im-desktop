package router

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import bean.IM
import bean.LoginResult
import bean.MessageToSend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import screen.LoginScreen
import screen.LoginState
import screen.MainScreen
import screen.RegisterScreen
import screen.model.ConnectState
import screen.model.MainModel
import utils.*
import java.io.File

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  14:54
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
class Router(initialState: MutableState<ScreenState>) {

    var routerState: MutableState<ScreenState> = initialState
    var context: Any? = null

    fun navigateTo(state: ScreenState, navigateContext: Any? = null) {
        routerState.value = state
        context = navigateContext
    }
}

@Composable
fun Router(
    scaffoldState: ScaffoldState
) {

    val router by remember {
        mutableStateOf(Router(mutableStateOf(ScreenState.Login)))
    }


    when (router.routerState.value) {
        is ScreenState.RegisterState -> {
            RegisterScreen(
                register = { user, pwd, name, sex, age ->
                    scope.launch {
                        val register = apiService.register(user, md5(pwd), sex, age, name)
                        if (register.code == 200) {
                            scaffoldState.snackbarHostState.showSnackbar("注册成功")
                            router.navigateTo(ScreenState.Login)
                        } else {
                            scaffoldState.snackbarHostState.showSnackbar(register.msg)
                        }
                    }
                },

                )
        }

        is ScreenState.Home -> {
            val data = router.context as LoginResult.Data
            val model = remember {
                MainModel()
            }

            //数据初始化
            SideEffect {
                scope.launch {
                    model.searchFriends(data.uid)
                }

                client.newWebSocket(
                    Request.Builder()
                        .url("ws://localhost:8080/socket/${data.uid}")
                        .build(), object : WebSocketListener() {

                        override fun onOpen(webSocket: WebSocket, response: Response) {
                            model.updateWsState(ConnectState.Connected(webSocket, response))
                        }

                        override fun onMessage(webSocket: WebSocket, text: String) {
                            val msg = gson.fromJson(text, IM::class.java)
                            model.post(msg)
                        }

                        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                            model.updateWsState(ConnectState.Failed(t, response))
                        }

                        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                            model.updateWsState(ConnectState.Closed)
                        }
                    }
                )
            }


            MainScreen(
                model,
                onAddClicked = {
                    model.searchFriends(data.uid)
                },
                onItemClicked = {
                    model.chat(data.uid, it.uid)
                    model.updateContact(it)
                },
                onSend = { text ->
                    when (val ws = model.wsConnect.value) {
                        is ConnectState.Idle -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("连接正在建立请稍后")
                            }
                        }

                        is ConnectState.Closed -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("连接已经关闭")
                            }
                        }

                        is ConnectState.Connected -> {
                            scope.launch {
                                val contacts = model.currentContact.value
                                val msg = MessageToSend(text, data.uid, contacts.uid)
                                ws.webSocket.send(gson.toJson(msg, MessageToSend::class.java))
                                model.clear()
                                //刷新页面状态
                                model.chat(msg.msgFrom, msg.msgTo)
                                model.searchFriends(data.uid)
                            }
                        }

                        else -> {}
                    }
                }
            )
        }

        is ScreenState.Login -> {
            val loginState = remember { LoginState() }
            LoginScreen(
                onLogin = { name, pwd ->
                    val coroutine: CoroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutine.launch {
                        scaffoldState.snackbarHostState.showSnackbar("开始")
                        try {
                            val login: LoginResult = apiService.login(name, md5(pwd))

                            if (login.code == 200) {
                                router.navigateTo(ScreenState.Home, login.data)
                                scaffoldState.snackbarHostState.showSnackbar("登陆成功")
                                loginState.error.value = false
                            } else {
                                scaffoldState.snackbarHostState.showSnackbar(login.msg)
                                loginState.error.value = true
                            }
                        } catch (e: Throwable) {
                            File("a.txt").writeText(e.stackTraceToString())
                        }
                    }
                },
                onRegister = {
                    router.navigateTo(ScreenState.RegisterState)
                },
                loginState = loginState
            )
        }

        else -> {}
    }
}