package com.mumulbo.notification.dto

data class ApiResponse<T>(
    val data: T,
    val success: Boolean = true,
    val message: String? = null
)
