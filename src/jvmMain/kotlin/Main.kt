import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import router.Router

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {

    val state = rememberWindowState()
    MaterialTheme {
        Window(
            onCloseRequest = ::exitApplication, icon = painterResource("wechat.svg"), title = "IM"
        ) {
            //包裹一层scaffold使用snackbar ha~
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                content = {
                    Router(scaffoldState)
                },
            )


        }
    }

}