package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.security.MessageDigest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd:HH:mm")
val scope = CoroutineScope(Dispatchers.IO)
//当前时间与之前时间进行对比，如果日期不一样采用yy/MM/dd格式
//否则采用HH:mm
fun dataFormatter(timeStamp: Long): String {
    val current = dateFormat.format(Date(System.currentTimeMillis()))
    val before = dateFormat.format(Date(timeStamp))
    val cur = current.split(":", "/")
    val bef = before.split(":", "/")
    return if (cur[0] != bef[0]) {
        // yy/MM/dd
        before.substring(2, 10)
    } else if (cur[1] != bef[1]) {
        // yy/MM/dd
        before.substring(2, 10)
    } else if (cur[2] == (bef[2].toInt() + 1).toString()) {
        "昨天"
    } else if(cur[2] != bef[2]) {
        // yy/MM/dd
        before.substring(2, 10)
    } else {
        // dd:HH:mm
        before.substring(11, 16)
    }
}

fun parseData(str: String): Long {
    return dateFormat.parse(str).time
}

fun md5(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val data = md.digest(str.toByteArray())


    return toHex(data)

}

fun toHex(byteArray: ByteArray): String {
    val result = with(StringBuilder()) {
        byteArray.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) {
                this.append("0").append(hexStr)
            } else {
                this.append(hexStr)
            }
        }
        this.toString()
    }
    //转成16进制后是32字节
    return result
}
