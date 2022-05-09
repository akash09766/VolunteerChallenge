package com.skylight.android.volunteering.core.di

import com.skylight.android.volunteering.core.utils.DataCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CacheModule {
    @Singleton
    @Provides
    fun provideDataCache() = DataCache()
}