package com.example.drinki

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

fun GetBackGroundColor() : Color
{
    return Color.White
}



fun sendSms(phoneNumber: String, message: String, context: Context)
{
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber")) //Wyświetlenie interfajsu do wysłania SMSa. Tworzy id zasobu w formacie sms:numertelefonu
    intent.putExtra("sms_body", message)    //Dodanie treści wiadomości
    context.startActivity(intent)   //Wypełnienie wiadomości (użytkownik musi potwierdzić przyciskiem "wyślij"

/*    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Brak aplikacji do wysyłania SMS", Toast.LENGTH_SHORT).show()
    }*/

    //Użycie
    //sendSms("123456789", "wiadomość", requireContext())
}