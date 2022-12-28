package router

import androidx.compose.material.AlertDialog
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screen.LoginScreen
import screen.LoginState
import screen.MainScreen
import screen.RegisterScreen
import test.list
import utils.apiService
import utils.md5

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  14:54
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
class Router(initialState: MutableState<ScreenState>) {

    var routerState: MutableState<ScreenState> = initialState

    fun navigateTo(state: ScreenState) {
        routerState.value = state
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
                register = { userName, pwd ->
                    router.navigateTo(ScreenState.Login)
                }
            )
        }

        is ScreenState.Home -> {
            MainScreen(list)
        }

        is ScreenState.Login -> {
            val loginState = remember { LoginState() }
            LoginScreen(
                onLogin = { name, pwd ->
                    val coroutine: CoroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutine.launch {
                        val login = apiService.login(name, md5(pwd))
                        if (login.code == 200) {
                            router.navigateTo(ScreenState.Home)
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