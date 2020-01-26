package com.example.mojapotrosnja.common

import java.util.*
import java.util.concurrent.ConcurrentHashMap


abstract class BaseObservable<LISTENER_CLASS> {

    // thread-safe set of listeners
    private val mListeners = Collections.newSetFromMap(
        ConcurrentHashMap<LISTENER_CLASS, Boolean>(1)
    )

    protected val listeners: Set<LISTENER_CLASS>
        get() = Collections.unmodifiableSet(mListeners)


    fun registerListener(listener: LISTENER_CLASS) {
        mListeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_CLASS) {
        mListeners.remove(listener)
    }

}