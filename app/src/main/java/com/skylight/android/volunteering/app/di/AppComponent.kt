package com.skylight.android.volunteering.app.di

import android.app.Application
import com.skylight.android.volunteering.app.application.FreeNowApplication
import com.skylight.android.volunteering.app.di.modules.PrefsModule
import com.skylight.android.volunteering.core.di.CacheModule
import com.skylight.android.volunteering.core.di.GoogleServiceModule
import com.skylight.android.volunteering.core.di.NetworkServiceModule
import com.skylight.android.volunteering.core.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Dagger component where all the created models will be defined
 */

@Singleton
@Component(
    modules = [
        // Dagger support
        AndroidInjectionModule::class,
        // Global
        NetworkServiceModule::class,
        GoogleServiceModule::class,
        ViewModelFactoryModule::class,
        CacheModule::class,
        // News feature
        FeatureBindingModule::class,
        PrefsModule::class
    ]
)
interface AppComponent : AndroidInjector<FreeNowApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    override fun inject(newsApp: FreeNowApplication)
}
