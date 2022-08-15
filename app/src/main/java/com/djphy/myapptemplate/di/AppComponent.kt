package com.djphy.myapptemplate.di

import android.content.Context
import com.djphy.myapptemplate.repo.MyHomeRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class]
)
interface AppComponent {

    fun inject(myHomeRepo: MyHomeRepo)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }
}