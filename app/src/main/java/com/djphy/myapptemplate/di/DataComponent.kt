package com.djphy.myapptemplate.di

import com.djphy.myapptemplate.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class
    ]
)
interface DataComponent {
    fun inject(viewModel: HomeViewModel)
}