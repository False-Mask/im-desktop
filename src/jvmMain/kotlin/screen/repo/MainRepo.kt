package screen.repo

import bean.*
import utils.apiService

object MainRepo {

    suspend fun getFriends(id: Int): Friends = apiService.friends(id)
    suspend fun chaList(from: Int, to: Int): Chat = apiService.chatList(from, to)

}