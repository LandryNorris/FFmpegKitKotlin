package com.example.ffmpegkit.stats

data class Statistics(val sessionId: Long, val videoFrameNumber: Int,
                      val videoFps: Float, val videoQuality: Float,
                      val size: Long, val time: Int,
                      val bitrate: Double, val speed: Double)
