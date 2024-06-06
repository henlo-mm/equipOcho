package com.appmovil.mediapp.webservice

import android.content.Context
import com.appmovil.mediapp.R
import com.google.api.client.extensions.android.util.store.FileDataStoreFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.gmail.GmailScopes
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

object GoogleAuth {
    private const val TOKENS_DIRECTORY_PATH = "tokens"

    fun getCredentials(context: Context): GoogleCredential {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.credentials)
        val clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), InputStreamReader(inputStream))

        val flow = GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            clientSecrets,
            listOf(CalendarScopes.CALENDAR, GmailScopes.GMAIL_SEND)
        ).setDataStoreFactory(FileDataStoreFactory(File(context.filesDir, TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()

        return GoogleCredential.Builder()
            .setTransport(GoogleNetHttpTransport.newTrustedTransport())
            .setJsonFactory(GsonFactory.getDefaultInstance())
            .setClientSecrets(clientSecrets.details.clientId, clientSecrets.details.clientSecret)
            .build()
    }
}