package com.example.ffmpegkit.callbacks

import com.example.ffmpegkit.stats.Statistics

fun interface StatisticsCallback {
    fun onStatistics(statistics: Statistics)
}