package com.skylight.android.volunteering.core.di


import dagger.Component

@Component(modules = [AppModule::class])
interface ApiComponent {
    fun inject(apiService: NetworkServiceModule)
}