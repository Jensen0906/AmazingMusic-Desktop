package org.may.amazingmusic.network

data class ApiResult<T> (
    var code: Int = -1,
    var msg: String? = null,
    var data: T? = null
)