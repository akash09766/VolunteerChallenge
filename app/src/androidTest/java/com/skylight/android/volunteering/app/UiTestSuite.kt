package com.skylight.android.volunteering.app

import com.skylight.android.volunteering.app.fragments.HomeScreenFragmentTest
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 26-02-2022 at 19:07.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeScreenFragmentTest::class,
    HomeScreenViewModelTest::class
)
class UiTestSuite