package test

import bean.Contacts
import bean.ContactsType
import utils.dataFormatter
import utils.parseData

val list = listOf(
    Contacts(
        name = "she",
        lastTime = parseData("2022/12/27:23:11"),
        lastMessage = "哈哈~",
        type = ContactsType.PERSON,
        icon = "https://dogefs.s3.ladydaily.com/~/source/unsplash/photo-1664575599736-c5197c684128?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=80"
    ),
    Contacts(
        name = "he",
        lastTime = parseData("2022/12/26:23:11"),
        lastMessage = "隔~",
        type = ContactsType.PERSON,
        icon = "https://plus.unsplash.com/premium_photo-1667682998399-3eee9dc2000d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxOXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=80"
    ),
    Contacts(
        name = "颜值天团",
        lastTime = parseData("2022/12/25:23:11"),
        lastMessage = "憋说了",
        type = ContactsType.GROUP,
        icon = "https://plus.unsplash.com/premium_photo-1669394041048-48d9a3b8f459?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw2N3x8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=80"
    ),
    Contacts(
        name = "🐕贼",
        lastTime = parseData("2022/12/25:23:11"),
        lastMessage = "你笑个🔨",
        type = ContactsType.PERSON,
        icon = "https://plus.unsplash.com/premium_photo-1670333351949-47f735fa9ba4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3M3x8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=80"
    ),
)

//tester
fun main() {
    val stamp = parseData("2022/12/27:23:11")
    println(dataFormatter(stamp))
}