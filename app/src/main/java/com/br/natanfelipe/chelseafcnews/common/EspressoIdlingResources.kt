package com.br.natanfelipe.chelseafcnews.common

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResources {

    const val RESOURCE = "GLOBAL"
    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if(!countingIdlingResource.isIdleNow) {
            Log.d("NATAN","${ countingIdlingResource.isIdleNow}")
            countingIdlingResource.decrement()
        }
    }
}
