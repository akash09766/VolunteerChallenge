package com.skylight.android.volunteering.core.di

import android.app.Application
import android.content.Context
import com.skylight.android.volunteering.app.api.ApiService
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideOkHttpClient(appContext: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(MConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}