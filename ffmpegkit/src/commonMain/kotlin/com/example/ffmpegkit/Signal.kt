package com.example.ffmpegkit

sealed class Signal(val value: Int) {
    object SigInt: Signal(2)
    object SigQuit: Signal(3)
    object SigPipe: Signal(13)
    object SigTerm: Signal(15)
    object SigXCpu: Signal(24)
}
