package com.example.ffmpegkit

fun argumentsToString(arguments: List<String>): String = buildString {
    arguments.forEachIndexed { index, arg ->
        if(index != 0) append(" ")
        append(arg)
    }
}
