package com.dreamers.postmodel

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    companion object {
        @JvmStatic
        val empty = Post(-1, -1, "", "")
    }

    val isEmpty: Boolean get() = this == empty

    fun hasMatch(query: String): Boolean {
        val queryRegex = Regex(query, RegexOption.IGNORE_CASE)
        return queryRegex.containsMatchIn(title) || queryRegex.containsMatchIn(body)
    }
}
