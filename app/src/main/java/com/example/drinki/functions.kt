package com.example.drinki

import android.content.Context
import android.content.Intent
import android.net.Uri

//Wysyłanie wiadomości na podany numer. Funkcja wykorzystuje wbudowaną aplikację do wysyłania sms
//i od wypełnia treść smsa wiadomością (użytkownik wysyła smsa z aplikacji do wysyłania smsów
fun sendSms(phoneNumber: String, message: String, context: Context)
{
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber")) //Wyświetlenie interfajsu do wysłania SMSa. Tworzy id zasobu w formacie sms:numertelefonu
    intent.putExtra("sms_body", message)    //Dodanie treści wiadomości
    context.startActivity(intent)   //Wypełnienie wiadomości (użytkownik musi potwierdzić przyciskiem "wyślij"
}