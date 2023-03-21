package com.example.ffmpegkit

data class ReturnCode(val value: Int)

val ReturnCode.isSuccess get() = value == SUCCESS
val ReturnCode.isCancel get() = value == CANCELLED
val ReturnCode.isError get() = !isSuccess && !isCancel

private const val SUCCESS = 0
private const val CANCELLED = 255
