package com.kualagames.shared.model.dto

import kotlinx.serialization.Serializable

//@Serializable
//data class WrapperDTO<out T>(
//    val item: T,
//)

typealias ListWrapperDTO<T> = Map<String, List<T>>