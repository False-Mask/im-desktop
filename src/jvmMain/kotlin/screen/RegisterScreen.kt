package screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    register: (userName: String, pwd: String, name: String, sex: String, age: Int) -> Unit = { _, _, _, _, _ -> },

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

    val (getAge, setAge) = remember {
        mutableStateOf("")
    }

    val (getName, setName) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        OutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
            value = getUserName,
            onValueChange = {
                if (it == "" || it.matches("[0-9]{1,10}".toRegex())) {
                    setUserName(it)
                }
            },
            singleLine = true,
            placeholder = {
                Text("账号")
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
            modifier = Modifier.padding(top = 10.dp),
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
            visualTransformation = PasswordVisualTransformation()

        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
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
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
            value = getName,
            onValueChange = setName,
            singleLine = true,
            placeholder = {
                Text("重新输入姓名")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource("name.svg"),
                    contentDescription = "leading icon",
                    modifier = Modifier.size(16.dp)
                )
            },
        )

        OutlinedTextField(modifier = Modifier.padding(top = 10.dp), value = getAge, onValueChange = {
            //设置只有输入数字时候才接受输入
            if (it == "" || it.matches("[0-9]{1,3}".toRegex())) {
                setAge(it)
            }
        }, singleLine = true, placeholder = {
            Text("输入年龄")
        }, isError = getAge != "" && (getAge.toInt() < 0 || getAge.toInt() > 100), leadingIcon = {
            Icon(
                painter = painterResource("age.svg"),
                contentDescription = "leading icon",
                modifier = Modifier.size(16.dp)
            )
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
        )


        var selectedIndex by remember {
            mutableStateOf(-1)
        }


        //读取用户性别
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("男")
            RadioButton(selected = selectedIndex == 0, onClick = {
                selectedIndex = 0
            })

            Spacer(modifier = Modifier.width(10.dp))

            Text("女")
            RadioButton(selected = selectedIndex == 1, onClick = {
                selectedIndex = 1
            })
        }



        OutlinedButton(modifier = Modifier.padding(10.dp), onClick = {
            register(
                getUserName, getPwd, getName,
                when (selectedIndex) {
                    0 -> {
                        "男"
                    }

                    1 -> {
                        "女"
                    }

                    else -> {
                        ""
                    }
                }, if (getAge == "") -1 else getAge.toInt()
            )
        }, content = {
            Text(
                "注册",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        })

    }

}