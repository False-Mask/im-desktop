package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *@author ZhiQiang Tu
 *@time 2022/12/26  18:12
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
//用于通知用户登陆失败，警示用户的bean类
data class LoginState(
    val error: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
@Preview
fun LoginScreen(
    onLogin: (userName: String, pwd: String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {},
    loginState: LoginState,
) {

    //账户
    val (getUserName, setUserName) = remember {
        mutableStateOf("")
    }
    //密码
    val (getPwd, setPwd) = remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        //用户的图标
        Image(
            painter = painterResource("icon.jpg"),
            contentDescription = "用户头像",
            modifier = Modifier.padding(10.dp).size(100.dp).clip(RoundedCornerShape(10.dp))
        )

        //用户名
        Text(
            modifier = Modifier.padding(10.dp),
            fontSize = 20.sp,
            text = "请登陆",
        )
        //用户名输入
        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = getUserName,
            onValueChange = setUserName,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource("user.svg"),
                    contentDescription = "user leading icon",
                    modifier = Modifier.size(16.dp)
                )
            },
            placeholder = {
                Text("用户名")
            },
            isError = loginState.error.value
        )
        //密码输入框
        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = getPwd,
            onValueChange = setPwd,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource("pwd.svg"),
                    contentDescription = "leading pwd icon",
                    modifier = Modifier.size(16.dp),
                )
            },
            placeholder = {
                Text("密码")
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = loginState.error.value
        )
        //注册用户 & 登陆
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 10.dp)
        ) {
            TextButton(
                onClick = onRegister,
                content = {
                    Text("没有账号?点击注册")
                }
            )
            TextButton(
                onClick = {
                    onLogin(getUserName, getPwd)
                },
                content = {
                    Text("登陆")
                },
            )
        }

    }

}
