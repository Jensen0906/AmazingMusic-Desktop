package org.may.amazingmusic.network

import org.may.amazingmusic.bean.KuwoSong
import retrofit2.http.GET
import retrofit2.http.Query

interface NetApi {

    @GET("/")
    suspend fun searchSongResult(
        @Query("name") keyword: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ApiResult<List<KuwoSong>?>

}