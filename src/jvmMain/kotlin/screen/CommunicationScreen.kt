package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import style.searchBackgroundColor
import utils.dataFormatter
import utils.parseData
import java.net.URL


@Composable
fun CommunicationScreen(
    list: List<Contacts>,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts, Int) -> Unit = { _, _ -> }

) {

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
        Messages()
    }

}

@Composable
fun Contacts(
    list: List<Contacts>,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts, Int) -> Unit = { _, _ -> }
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
    onItemClicked: (Contacts, Int) -> Unit = { _, _ -> }
) {
    LazyColumn {
        itemsIndexed(list, itemContent = { index: Int, item: Contacts ->
            Item(item, onItemClicked = {
                onItemClicked(item, index)
            })
        })
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
                modifier = Modifier.size(40.dp),
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
                        textAlign = TextAlign.Center,
                    )
                }
                Text(
                    contact.lastMessage,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
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
fun Messages() {

}
