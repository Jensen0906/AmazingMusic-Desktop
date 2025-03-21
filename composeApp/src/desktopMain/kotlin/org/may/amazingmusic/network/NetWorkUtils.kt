package org.may.amazingmusic.network

import org.may.amazingmusic.constants.NetWorkConst.KUWO_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetWorkUtils {
    private val kuwoRetrofit = Retrofit.Builder()
        .baseUrl(KUWO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getKuwoApi(): NetApi = kuwoRetrofit.create()
}