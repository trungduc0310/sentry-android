package com.tcom.logingsdk

import io.sentry.Sentry
import io.sentry.SentryEvent
import io.sentry.SentryLevel
import io.sentry.UserFeedback
import io.sentry.protocol.User
import java.lang.Exception

class LogCapture {
    private var user: User? = null

    constructor(userBuilder: UserBuilder) {
        this.user = userBuilder.getUser()
    }

    fun captureMessage(message: String, levelCapture: LevelCapture) {
        when (levelCapture) {
            LevelCapture.DEBUG -> {
                Sentry.captureMessage(message, SentryLevel.DEBUG)
            }
            LevelCapture.INFO -> {
                Sentry.captureMessage(message, SentryLevel.INFO)
            }
            LevelCapture.WARNING -> {
                Sentry.captureMessage(message, SentryLevel.WARNING)
            }
            LevelCapture.ERROR -> {
                Sentry.captureMessage(message, SentryLevel.ERROR)
            }
            LevelCapture.FATAL -> {
                Sentry.captureMessage(message, SentryLevel.FATAL)
            }
        }
    }

    fun captureMessage(message: String) {
        captureMessage(message, LevelCapture.DEBUG)
    }

    fun captureExeption(exception: Exception) {
        Sentry.captureException(exception)
    }

    fun captureEvent(throwable: Throwable) {
        Sentry.captureEvent(SentryEvent(throwable))
    }

    fun captureFeedback(comment: String) {
        val feedback = UserFeedback(Sentry.captureEvent(SentryEvent()))
        feedback.comments = comment
        feedback.email = user?.email
        feedback.name = user?.username
        Sentry.captureUserFeedback(feedback)
    }

    public enum class LevelCapture {
        DEBUG, INFO, WARNING, ERROR, FATAL
    }
}