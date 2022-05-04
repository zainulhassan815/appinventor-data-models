package com.dreamers.postmodel

import com.google.appinventor.components.annotations.SimpleEvent
import com.google.appinventor.components.annotations.SimpleFunction
import com.google.appinventor.components.annotations.SimpleProperty
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent
import com.google.appinventor.components.runtime.ComponentContainer
import com.google.appinventor.components.runtime.EventDispatcher
import com.google.appinventor.components.runtime.util.YailList
import com.google.gson.Gson
import com.google.gson.JsonParseException

@Suppress("FunctionName")
class PostModel(container: ComponentContainer) : AndroidNonvisibleComponent(container.`$form`()) {

    private val gson = Gson()
    private var _currentPost: Post? = null

    @SimpleFunction(
        description = "Create a new post."
    )
    fun CreatePost(userId: Int, id: Int, title: String, body: String): Any {
        return Post(userId, id, title, body)
    }

    @SimpleFunction(
        description = "Create a new post object from JSON string."
    )
    fun PostFromJson(json: String): Any? {
        return try {
            gson.fromJson(json, Post::class.java)
        } catch (e: JsonParseException) {
            OnJsonParseError(json, e.message ?: e.toString())
            null
        }
    }

    @SimpleFunction(
        description = "Create a list of posts from JSON Array string."
    )
    fun PostListFromJson(json: String): YailList? {
        return try {
            val type = ParameterizedTypeList(Post::class.java)
            val posts: List<Post> = gson.fromJson(json, type)
            YailList.makeList(posts)
        } catch (e: JsonParseException) {
            OnJsonParseError(json, e.message ?: e.toString())
            null
        }
    }

    @SimpleFunction(description = "Convert a single post or list of posts to valid JSON string.")
    fun toJson(data: Any): String {
        return gson.toJson(data)
    }

    @SimpleFunction(
        description = "Check whether given post has data or not. Returns true if the given object is empty, null or is not a type of post."
    )
    fun IsEmpty(post: Any): Boolean {
        return if (post is Post) post.isEmpty else true
    }

    @SimpleFunction(
        description = "Check whether given post has data or not. Returns true if the given object is not empty, not null and is a type of post."
    )
    fun IsNotEmpty(post: Any): Boolean {
        return !IsEmpty(post)
    }

    @SimpleFunction(
        description = "Set value of current post in order to access other properties. Return true if CurrentPost was set successfully."
    )
    fun SetCurrentPost(post: Any): Boolean {
        if (post is Post) {
            _currentPost = post
            return true
        }
        return false
    }

    @SimpleEvent(
        description = "Event raised when an error occurs while deserializing JSON."
    )
    fun OnJsonParseError(inputJson: String, message: String) {
        EventDispatcher.dispatchEvent(this, "OnJsonParseError", inputJson, message)
    }

    @SimpleProperty(
        description = "Get current post object set using SetCurrentPost. Returns null if no object is set."
    )
    fun CurrentPost(): Any? = _currentPost

    @SimpleProperty
    fun UserId(): Int = _currentPost?.userId ?: -1

    @SimpleProperty
    fun Id(): Int = _currentPost?.id ?: -1

    @SimpleProperty
    fun Title(): String? = _currentPost?.title

    @SimpleProperty
    fun Body(): String? = _currentPost?.body

}
