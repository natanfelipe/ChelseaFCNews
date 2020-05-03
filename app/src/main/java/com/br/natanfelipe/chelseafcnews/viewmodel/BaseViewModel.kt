package com.br.natanfelipe.chelseafcnews.viewmodel

import androidx.lifecycle.ViewModel
import com.br.natanfelipe.chelseafcnews.interfaces.IViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope, IViewModel {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}