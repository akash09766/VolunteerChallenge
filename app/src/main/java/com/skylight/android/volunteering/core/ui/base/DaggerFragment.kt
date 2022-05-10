package com.skylight.android.volunteering.core.ui.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.skylight.android.volunteering.app.util.Prefs
import com.skylight.android.volunteering.app.util.formatDate
import com.skylight.android.volunteering.core.di.base.Injectable
import com.skylight.android.volunteering.core.utils.DataCache
import dagger.android.DispatchingAndroidInjector
import java.util.*
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

    fun showDatePickerDialog(inputView: TextInputEditText) {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selected = Calendar.getInstance()
                selected.set(Calendar.YEAR, year)
                selected.set(Calendar.MONTH, monthOfYear)
                selected.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                inputView.setText(selected.formatDate())
            },
            y,
            m,
            d
        )
        dpd.datePicker.minDate = c.timeInMillis
        dpd.show()
    }

    fun showTimePickerDialog(inputView: TextInputEditText) {
        val currentTime = Calendar.getInstance()
        val hour: Int = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            { _, hourOfDay, minute ->
                inputView.setText("$hourOfDay:$minute")
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}
