package com.example.ffmpegkit

import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession
import com.example.ffmpegkit.sessions.MediaInformationSession
import com.example.ffmpegkit.sessions.Session

private val sessionsMap = mutableMapOf<Long, Session>()
private val sessions = mutableListOf<Session>()
private var historySize = 100

val lastSession get() = sessions.last()
val lastCompleted get() = sessions.lastOrNull { it.state == SessionState.COMPLETED }

val ffmpegSessions get() = sessions.filterIsInstance<FFmpegSession>()
val ffprobeSessions get() = sessions.filterIsInstance<FFprobeSession>()
val mediaInformationSessions get() = sessions.filterIsInstance<MediaInformationSession>()

fun setMaxHistorySize(newSize: Int) {
    historySize = newSize
    trimToSize()
}

fun addSession(session: Session) {
    val sessionAlreadyAdded = sessionsMap.containsKey(session.sessionId)
    if(!sessionAlreadyAdded) {
        sessionsMap[session.sessionId] = session
        sessions.add(session)
        trimToSize()
    }
}

private fun trimToSize() {
    while(sessions.size > historySize) {
        val oldest = if(sessions.isNotEmpty()) sessions.removeAt(0) else null
        if(oldest != null) {
            sessionsMap.remove(oldest.sessionId)
        }
    }
}

fun clearSessions() {
    sessions.clear()
    sessionsMap.clear()
}
