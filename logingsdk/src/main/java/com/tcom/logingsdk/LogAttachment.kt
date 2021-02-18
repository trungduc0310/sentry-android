package com.tcom.logingsdk

import io.sentry.Attachment
import io.sentry.Sentry
import java.io.FileInputStream

class LogAttachment {
    private fun getData(localPath: String): ByteArray? {
        return try {
            var byteArray = ByteArray(2048)
            FileInputStream(localPath).read(byteArray)
            byteArray
        } catch (ex: Exception) {
            Sentry.captureException(ex)
            null
        }
    }

    fun addAttachment(localPath: String, fileName: String) {
        Sentry.withScope { scope ->
            scope.addAttachment(
                Attachment(
                    getData(localPath)!!, fileName, "application/octet-stream"
                    , true
                )
            )
        }
    }
}