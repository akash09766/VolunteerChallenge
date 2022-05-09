package com.skylight.android.volunteering.core.di

import androidx.lifecycle.ViewModelProvider
import com.skylight.android.volunteering.core.di.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}