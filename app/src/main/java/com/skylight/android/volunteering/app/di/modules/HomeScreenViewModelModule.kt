package com.skylight.android.volunteering.app.di.modules

import androidx.lifecycle.ViewModel
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.di.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * creating model for [HomeScreenViewModel] using dagger multi binding technique
 */

@Module
interface HomeScreenViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    fun bindHomeScreenViewModel(homeScreenViewModel: HomeScreenViewModel): ViewModel
}