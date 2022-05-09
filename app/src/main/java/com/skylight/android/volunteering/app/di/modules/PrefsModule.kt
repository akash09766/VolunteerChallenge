package com.skylight.android.volunteering.app.di.modules

import android.app.Application
import com.skylight.android.volunteering.app.util.Prefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 07-04-2022 at 04:13.
 */
@Module
object PrefsModule {

    @Singleton
    @Provides
    fun providePres(application: Application) = Prefs(application)
}