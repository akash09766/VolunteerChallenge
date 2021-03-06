package com.skylight.android.volunteering.app.dataSource

import org.junit.Before
import org.mockito.MockitoAnnotations

/**
 * Base class to support mocking
 * in tests.
 *
 * Example:
 * ```
 * class AbcTest: MockitoTest {
 *    @Mock
 *    lateinit var a: Abc
 * }
 *
 * ```
 */
abstract class MockitoTest {

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}