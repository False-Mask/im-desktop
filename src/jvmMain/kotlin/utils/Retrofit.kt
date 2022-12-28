package utils

import api.ApiService
import config.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  15:50
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


val apiService = retrofit.create(ApiService::class.java)