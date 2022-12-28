package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import bean.Contacts
import style.itemSelectColor
import style.itemUnSelectedColor
import style.navBackgroundColor

@Composable
fun MainScreen(
    list: List<Contacts>,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts, Int) -> Unit = { _, _ -> }
) {

    val (selectedItem, selectedItemChanged) = remember {
        mutableStateOf(0)
    }

    Row {
        LeftNav(selectedItem = selectedItem, onSelectedChanged = selectedItemChanged)
        RightContent(
            list = list,
            onAddClicked = onAddClicked,
            onItemClicked = onItemClicked
        )

    }
}

@Composable
fun LeftNav(
    selectedItem: Int,
    onSelectedChanged: (Int) -> Unit = {},
) {
    Surface(color = navBackgroundColor) {

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource("icon.jpg"),
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                contentDescription = "头像",
            )

            NavIconButton(
                onSelectedChanged = {
                    onSelectedChanged(it)
                },
                selectedItem = selectedItem,
                resource = "chat.svg",
                selectedIndex = 0
            )

            NavIconButton(
                onSelectedChanged = {
                    onSelectedChanged(it)
                },
                selectedItem = selectedItem,
                resource = "friend.svg",
                selectedIndex = 1
            )

            Spacer(Modifier.size(0.dp))

        }

    }
}

@Composable
fun NavIconButton(
    onSelectedChanged: (Int) -> Unit,
    resource: String,
    selectedIndex: Int,
    selectedItem: Int
) {
    Icon(
        painter = painterResource(resource),
        modifier = Modifier
            .padding(top = 16.dp)
            .size(26.dp)
            .clickable {
                onSelectedChanged(selectedIndex)
            },
        tint = if (selectedIndex == selectedItem) itemSelectColor else itemUnSelectedColor,
        contentDescription = null,
    )
}

@Composable
fun RightContent(
    list: List<Contacts>,
    onAddClicked: () -> Unit = {},
    onItemClicked: (Contacts, Int) -> Unit = { _, _ -> }
) {
    CommunicationScreen(list, onAddClicked = onAddClicked, onItemClicked = onItemClicked)
}
