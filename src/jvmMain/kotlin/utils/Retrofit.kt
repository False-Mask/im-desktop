package utils

import api.ApiService
import com.google.gson.Gson
import config.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@author ZhiQiang Tu
 *@time 2022/12/28  15:50
 *@signature 我将追寻并获取我想要的答案
 *@mail  2623036785@qq.com
 */
val client = OkHttpClient()

val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

val gson = Gson()

val apiService = retrofit.create(ApiService::class.java)

