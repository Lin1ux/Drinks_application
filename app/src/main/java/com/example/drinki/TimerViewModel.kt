package com.example.drinki

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

//View Model, który trzyma wartości między stanami
class TimerViewModel : ViewModel(), LifecycleObserver
{
    //mutableStateOf zapisuje wartości zmiennych podczas zmiany stanu
    var duration by mutableIntStateOf(5)            //Czas trwania timera
    var time by mutableIntStateOf(5)                //Pozostały czas
    var isRunning by mutableStateOf(false)          //Czy timer działa
    var canSetTime : Boolean = true                 //Czy time może zostać zmieniony

    var canCount = false

    //Zatrzymanie timera kiedy użytkownik minimalizuje aplikajce
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) //Wywołanie kiedy użytkownik ma zminimalizowaną aplikację
    fun unactive()
    {
        canCount = false
    }

    //Włączenie timera kiedy użytkownik widzi aplikacje
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) //Wywołanie kiedy użytkownik ma wyświetloną aplikacje
    fun active()
    {
        canCount = true
    }
    fun setNewDuration(value : Int)
    {
        duration = value
        if(canSetTime)
        {
            time = duration
            canSetTime = false
        }
    }
    fun ResetAndSetDuration(value : Int)
    {
        resetTimer()
        setNewDuration(value)
    }
    //Zmiana stanu timera
    fun switchTimer()
    {
        isRunning = !isRunning
    }
    //Zatrzymanie timer
    fun stopTimer()
    {
        isRunning = false
    }

    //Resetowanie wartości timera
    fun resetTimer()
    {
        time = duration
        isRunning = false
        canSetTime = true
    }
    //Aktualizacja timera
    fun updateTime()
    {
        //Jeśli aplikacja nie jest wyświetlana nie pozwala liczyć
        if(!canCount)
        {
            return
        }
        //zmniejszenie wartości licznika
        if (isRunning && time > 0)
        {
            time--
        }
        else if (time <= 0)
        {
            resetTimer()
        }
    }
}