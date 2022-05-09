package com.skylight.android.volunteering.core.di

import com.skylight.android.volunteering.app.api.GoogleService
import com.skylight.android.volunteering.app.util.MConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object GoogleServiceModule {

    @Singleton
    @Provides
    fun provideGoogleService(@Named("Google") retrofit: Retrofit): GoogleService =
        retrofit.create(GoogleService::class.java)

    @Provides
    @Singleton
    @Named("Google")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MConstants.G_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}