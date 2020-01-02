package com.base.commons

import android.view.View
import com.base.commons.AnimUtils.startFadeInAnimation
import com.base.commons.AnimUtils.startFadeOutAnimation
import com.base.commons.AnimUtils.startSpringResize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.splashAnimation(functionOnFinish: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        startSpringResize(this@splashAnimation)
        delay(2000)
        functionOnFinish()
    }
}


fun View.visibileIf(showing: Boolean) {
    if (showing) {
        startFadeInAnimation(this, 300L)
    } else {
        startFadeOutAnimation(this, 300L)
    }
}

fun <T> ArrayList<T>?.replaceData(newList: List<T>) {
    this?.let {
        clear()
        addAll(newList)
    }
}

fun <E> Collection<E>.toArrayList(): ArrayList<E> {
    return ArrayList<E>().apply {
        addAll(this)
    }
}
