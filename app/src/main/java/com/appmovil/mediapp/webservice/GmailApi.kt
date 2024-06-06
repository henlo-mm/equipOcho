package com.appmovil.mediapp.webservice

import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import java.util.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.Session
import java.io.ByteArrayOutputStream
import javax.mail.MessagingException

object GmailApi {
    fun sendEmail(service: Gmail, to: String, from: String, subject: String, bodyText: String) {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)
        val email = MimeMessage(session)

        try {
            email.setFrom(InternetAddress(from))
            email.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(to))
            email.subject = subject
            email.setText(bodyText)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }

        val buffer = ByteArrayOutputStream()
        email.writeTo(buffer)
        val rawMessage = Message()
        rawMessage.encodeRaw(buffer.toByteArray())
        service.users().messages().send("me", rawMessage).execute()
    }
}