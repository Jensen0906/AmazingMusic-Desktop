package org.may.amazingmusic.bean

class KuwoSong {
    var rid: Long = 0
    var vid: String? = null
    var name: String? = null
    var artist: String? = null
    var pic: String? = null
    var lrc: String? = null
    var url: String? = null
    var uid = -1
    var isFavorite = false

    override fun toString(): String {
        return "KuwoSong(rid=$rid, vid=$vid, name=$name, artist=$artist, pic=$pic, lrc=$lrc, url=$url, uid=$uid, isFavorite=$isFavorite)"
    }
}
