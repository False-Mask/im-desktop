package router

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import bean.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screen.LoginScreen
import screen.LoginState
import screen.MainScreen
import screen.RegisterScreen
import screen.model.MainModel
import utils.apiService
import utils.md5
import utils.scope

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
            }

            MainScreen(
                model,
                onAddClicked = {
                    model.searchFriends(data.uid)
                },
                onItemClicked = {
                    model.chat(data.uid,it.uid)
                },
            )
        }

        is ScreenState.Login -> {
            val loginState = remember { LoginState() }
            LoginScreen(
                onLogin = { name, pwd ->
                    val coroutine: CoroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutine.launch {
                        val login = apiService.login(name, md5(pwd))
                        if (login.code == 200) {
                            router.navigateTo(ScreenState.Home, login.data)
                            scaffoldState.snackbarHostState.showSnackbar("登陆成功")
                            loginState.error.value = false
                        } else {
                            scaffoldState.snackbarHostState.showSnackbar(login.msg)
                            loginState.error.value = true
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