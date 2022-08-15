package com.djphy.myapptemplate

import android.app.Application
import com.djphy.myapptemplate.di.AppComponent
import com.djphy.myapptemplate.di.DaggerAppComponent
import com.djphy.myapptemplate.di.DaggerDataComponent
import com.djphy.myapptemplate.di.DataComponent

class MyApp : Application() {

    private val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    private val dataComponent : DataComponent by lazy {
        DaggerDataComponent.create()
    }

    init {
        instance = this
    }

    companion object{
        private lateinit var instance: MyApp

        fun getApp() : MyApp {
            return instance
        }

        fun appComponent(): AppComponent {
            return instance.appComponent
        }

        fun dataComponent(): DataComponent {
            return instance.dataComponent
        }
    }
}