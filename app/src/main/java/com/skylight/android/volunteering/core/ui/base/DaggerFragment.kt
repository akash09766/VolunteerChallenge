package com.skylight.android.volunteering.core.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skylight.android.volunteering.app.util.Prefs
import com.skylight.android.volunteering.core.di.base.Injectable
import com.skylight.android.volunteering.core.utils.DataCache
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


// Easy to switch base fragment in future
typealias BaseFragment = DaggerFragment

/**
 * Base fragment providing Dagger support and [ViewModel] support
 */
abstract class DaggerFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    Injectable {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dataCache: DataCache

    @Inject
    lateinit var prefs: Prefs
}
