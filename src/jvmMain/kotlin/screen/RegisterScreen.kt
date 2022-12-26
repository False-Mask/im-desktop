package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    register: (userName: String, pwd: String) -> Unit = { _, _ -> }
) {

    val (getUserName, setUserName) = remember {
        mutableStateOf("")
    }

    val (getPwd, setPwd) = remember {
        mutableStateOf("")
    }

    val (getRepwd, setRepwd) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp),
            value = getUserName,
            onValueChange = setUserName,
            singleLine = true,
            placeholder = {
                Text("用户名")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource("user.svg"),
                    contentDescription = "leading user icon",
                    modifier = Modifier.size(16.dp)
                )
            },
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp),
            value = getPwd,
            onValueChange = setPwd,
            singleLine = true,
            placeholder = {
                Text("密码")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource("pwd.svg"),
                    contentDescription = "leading pwd icon",
                    modifier = Modifier.size(16.dp)
                )
            },

            )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp),
            value = getRepwd,
            onValueChange = setRepwd,
            isError = getPwd != getRepwd && getRepwd != "",
            singleLine = true,
            placeholder = {
                Text("重新输入密码")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource("pwd.svg"),
                    contentDescription = "leading repwd icon",
                    modifier = Modifier.size(16.dp)
                )
            },
        )

        OutlinedButton(
            modifier = Modifier.padding(10.dp),
            onClick = {
                register(getUserName, getPwd)
            },
            content = {
                Text(
                    "注册",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        )

    }

}