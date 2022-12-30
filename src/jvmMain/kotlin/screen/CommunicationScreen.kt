package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bean.Contacts
import bean.ContactsType
import bean.MessageItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import screen.model.MainModel
import style.*
import utils.dataFormatter
import utils.parseData
import utils.scope
import java.net.URL


@Composable
fun CommunicationScreen(
    model: MainModel,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts) -> Unit = { _ -> },
    onSend: (String) -> Unit = { _ -> }
) {

    val list by model.listData.collectAsState()

    Row {
        Contacts(
            list = list,
            onAddClicked = onAddClicked,
            onItemClicked = onItemClicked,
        )
        Divider(
            modifier = Modifier.width(1.dp)
                .fillMaxHeight()
        )
        Messages(model = model, onSend = onSend)
    }

}

@Composable
fun Contacts(
    list: List<Contacts>,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts) -> Unit = { _ -> }
) {
    Column(
        modifier = Modifier.width(260.dp)
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            SearchBar(onAddClicked)
        }
        Lists(list = list, onItemClicked = onItemClicked)
    }
}

@Composable
fun SearchBar(onAddClicked: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.width(180.dp)) {
            Search()
        }
        Icon(
            painterResource("add.svg"), contentDescription = null, modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable(onClick = onAddClicked)
                .background(color = searchBackgroundColor)

        )
    }
}


@Composable
fun Lists(
    list: List<Contacts>,
    onItemClicked: (Contacts) -> Unit = { _ -> }
) {
    LazyColumn {
        items(list) { item ->
            Item(item, onItemClicked = {
                onItemClicked(item)
            })
        }
    }
}


@Composable
@Preview
fun ItemPreview() {

    val contact = Contacts(
        name = "aaaaaaaaaaaaaaaaaa",
        lastTime = parseData("2022/12/27:23:11"),
        icon = "https://images.unsplash.com/photo-1667162964142-577959f9cefd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60",
        lastMessage = "I'm somewhere over Memphis and you're still in bed..",
        type = ContactsType.GROUP,
    )

    Box(
        modifier = Modifier.width(260.dp)
            .background(Color.Cyan)
    ) {
        Item(contact)

    }
}

@Composable
fun Item(
    contact: Contacts,
    onItemClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onItemClicked)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row {
            Image(
                bitmap = loadImageBitmap(
                    inputStream = URL(contact.icon).openStream().buffered()
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = contact.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(70.dp),
                    )
                    val timeStamp = contact.lastTime
                    Text(
                        text = dataFormatter(timeStamp),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Thin,
                        maxLines = 1,
                    )
                }
                Text(
                    contact.lastMessage,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }


    }
}

@Composable
fun Search() {
    Surface(
        color = searchBackgroundColor,
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
    ) {
        val (getV, setV) = remember {
            mutableStateOf("")
        }
        BasicTextField(
            value = getV,
            onValueChange = setV,
            modifier = Modifier
                .padding(start = 10.dp)
                .height(25.dp),
            singleLine = true,
            decorationBox = {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource("search.svg"), contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (getV == "") {
                            Text("搜索", fontSize = 14.sp, color = Color.Gray)
                        }
                        it()
                    }
                }
            }
        )

    }
}


@Composable
fun Messages(
    model: MainModel,
    onSend: (String) -> Unit = { _ -> },
) {

    val chats by model.chat.collectAsState()

    Column {
        Box(
            modifier = Modifier.fillMaxSize()
                .weight(3.0f)
                .background(chatBackgroundColor),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(chats) {
                    MessageItem(data = it)
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
        Box(
            modifier = Modifier.weight(2.0f)
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Input(onSend, model)
        }
    }

}

@Composable
fun Input(
    onSend: (String) -> Unit,
    model: MainModel
) {
    val (input, inputChange) = remember {
        mutableStateOf("")
    }

    SideEffect {
        scope.launch {
            model.clearEvent.collect {
                inputChange("")
            }
        }
    }

    Box {
        BasicTextField(
            value = input,
            onValueChange = inputChange,
            modifier = Modifier.fillMaxSize()
        )
        Button(
            onClick = {
                if (input != "") {
                    onSend(input)
                }
            },
            content = {
                Text(
                    "发送", color = chatSendFontColor,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = chatSendBackground
            )
        )
    }
}

@Preview
@Composable
fun MessageItemTest() {

    val l = listOf(
        MessageItem(
            false,
            "https://dogefs.s3.ladydaily.com/~/source/unsplash/photo-1667589327857-059c79de243e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=80",
            "吻就落在耳后，阻隔喧嚣缺口。",
            1672289180730L,
        ),
        MessageItem(
            true,
            "https://dogefs.s3.ladydaily.com/~/source/unsplash/photo-1667589327857-059c79de243e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=80",
            "吻就落在耳后，阻隔喧嚣缺口。",
            1672289180730L,
        ),
        MessageItem(
            true,
            "https://dogefs.s3.ladydaily.com/~/source/unsplash/photo-1667589327857-059c79de243e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=80",
            "吻就落在耳后，阻隔喧嚣缺口。",
            1672289180730L,
        )

    )

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFf5f5f5))
    ) {

        LazyColumn {
            items(l) { item ->
                MessageItem(data = item)
            }
        }

    }

}

@Composable
fun MessageItem(
    data: MessageItem = MessageItem()
) {
    if (data.isSelfMessage) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()

        ) {
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(selfChatContentColor)
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = data.message,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            //头像
            Image(
                bitmap = loadImageBitmap(
                    inputStream = URL(data.profile).openStream().buffered()
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 40.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
        }
    } else {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
        ) {
            //头像
            Image(
                bitmap = loadImageBitmap(
                    inputStream = URL(data.profile).openStream().buffered()
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 40.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .padding(end = 40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(contactChatContentColor)
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = data.message,
                )
            }
        }

    }

}

