package com.djphy.myapptemplate.di

import com.djphy.myapptemplate.repo.MyHomeRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideHomeRepositoryI(): MyHomeRepo {
        return MyHomeRepo()
    }
}