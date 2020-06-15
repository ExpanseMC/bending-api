package com.expansemc.bending.api.util

import com.google.common.reflect.TypeToken

inline fun <reified T> typeToken(): TypeToken<T> = object : TypeToken<T>() {}