package com.skylight.android.volunteering.app.di

import com.skylight.android.volunteering.app.activities.MainActivity
import com.skylight.android.volunteering.app.dataSource.DataRepositoryModule
import com.skylight.android.volunteering.app.di.modules.HomeScreenViewModelModule
import com.skylight.android.volunteering.app.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Describes list of activities which require
 * DI.
 *
 * Each [ContributesAndroidInjector] generates a sub-component
 * for each activity under the hood
 */
@Module(
    includes = [
        HomeScreenViewModelModule::class,
        DataRepositoryModule::class,
    ]
)
interface FeatureBindingModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeHomeScreenFragment(): HomeScreenFragment

    @ContributesAndroidInjector
    fun contributeCreateEventFragment(): CreateEventFragment

    @ContributesAndroidInjector
    fun contributeCreateSessionFragment(): CreateSessionFragment

    @ContributesAndroidInjector
    fun contributeEventsListFragment(): EventsListFragment

    @ContributesAndroidInjector
    fun contributeDummyUserChooserFragment(): DummyUserChooserFragment

    @ContributesAndroidInjector
    fun contributeAddSpeakerFragment(): AddSpeakerFragment

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeSplashScreenFragment(): SplashScreenFragment

    @ContributesAndroidInjector
    fun contributeAdminHomeScreenFragment(): AdminHomeScreenFragment

    @ContributesAndroidInjector
    fun contributeCreateVolunteerFragment(): CreateVolunteerFragment

    @ContributesAndroidInjector
    fun contributeCreateOrganisationFragment(): CreateOrganisationFragment

    @ContributesAndroidInjector
    fun contributeEventDetailsFragment(): EventDetailsFragment

    @ContributesAndroidInjector
    fun contributeMapsFragment(): MapsFragment
}