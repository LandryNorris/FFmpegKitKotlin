package com.example.ffmpegkit

sealed interface LogRedirectionStrategy {
    object AlwaysPrintLogs: LogRedirectionStrategy
    object PrintLogsWhenNoCallbackDefined: LogRedirectionStrategy
    object PrintLogsWhenGlobalCallbackNotDefined: LogRedirectionStrategy
    object PrintLogsWhenSessionCallbackNotDefined: LogRedirectionStrategy
    object NeverPrintLogs: LogRedirectionStrategy
}