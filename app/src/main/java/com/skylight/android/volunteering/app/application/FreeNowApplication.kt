package com.skylight.android.volunteering.app.application

import android.app.Application
import com.skylight.android.volunteering.BuildConfig
import com.skylight.android.volunteering.app.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 02:50.
 * Dependency injection using dagger android injector technique
 */
class FreeNowApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        // Init DI magic ✨
        AppInjector.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
