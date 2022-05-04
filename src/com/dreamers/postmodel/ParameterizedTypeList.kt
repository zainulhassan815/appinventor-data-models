package com.dreamers.postmodel

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ParameterizedTypeList<T>(private val wrapped: Class<T>) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type> = arrayOf(wrapped)

    override fun getRawType(): Type = List::class.java

    override fun getOwnerType(): Type? = null
}