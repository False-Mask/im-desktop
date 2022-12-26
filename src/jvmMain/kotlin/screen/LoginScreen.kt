package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

/**
 *@author ZhiQiang Tu
 *@time 2022/12/26  18:12
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */

@Composable
@Preview
fun LoginScreen(
    onLogin: (userName: String, pwd: String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {},
) {

    val (getUserName, setUserName) = remember {
        mutableStateOf("")
    }

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
        OutlinedTextField(modifier = Modifier.padding(10.dp),
            value = getUserName,
            onValueChange = setUserName,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource("user.svg"),
                    contentDescription = "user Lleading icon",
                    modifier = Modifier.size(16.dp)
                )
            },
            placeholder = {
                Text("用户名")
            })
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
                    modifier = Modifier.size(16.dp)
                )
            },
            placeholder = {
                Text("密码")
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
        )
        //注册用户 & 登陆
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(top = 10.dp)) {
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
