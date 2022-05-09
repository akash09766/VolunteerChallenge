package com.skylight.android.volunteering

import com.skylight.android.volunteering.app.api.ApiServiceTest
import com.skylight.android.volunteering.app.dataSource.DataRepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 26-02-2022 at 19:19.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ApiServiceTest::class,
    DataRepositoryTest::class
)
class BackEndSuite