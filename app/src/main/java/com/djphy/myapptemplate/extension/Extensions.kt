package com.djphy.myapptemplate.extension

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
}