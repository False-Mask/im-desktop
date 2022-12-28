package api

import bean.LoginResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  15:48
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */

interface ApiService {
    @GET("/user/login")
    suspend fun login(
        @Query("userName") userName: String,
        @Query("pwd") pwd: String
    ): LoginResult

}

