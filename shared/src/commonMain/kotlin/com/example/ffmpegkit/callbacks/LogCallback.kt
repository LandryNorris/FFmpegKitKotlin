package com.example.ffmpegkit.callbacks

import com.example.ffmpegkit.Log

fun interface LogCallback {
    fun onLog(log: Log)
}
