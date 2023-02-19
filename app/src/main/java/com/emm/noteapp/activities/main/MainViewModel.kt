package com.emm.noteapp.activities.main

import androidx.lifecycle.ViewModel

inline fun <reified R> new(
    constructor: () -> R,
): R = constructor()

class MainViewModel: ViewModel() {

}