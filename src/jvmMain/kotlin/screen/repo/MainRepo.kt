package screen.repo

import bean.Contacts
import bean.Friends
import bean.User
import utils.apiService

object MainRepo {

    suspend fun getFriends(id: Int): Friends = apiService.friends(id)

}