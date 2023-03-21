package com.example.ffmpegkit

sealed interface SessionState {
    object CREATED: SessionState
    object RUNNING: SessionState
    object FAILED: SessionState
    object COMPLETED: SessionState
}
