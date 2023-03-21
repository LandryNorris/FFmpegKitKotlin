package com.example.ffmpegkit.callbacks

import com.example.ffmpegkit.sessions.FFmpegSession

interface FFmpegSessionCompleteCallback {
    fun onComplete(session: FFmpegSession)
}