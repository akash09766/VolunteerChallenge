package com.skylight.android.volunteering.core.utils


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.PorterDuff
import android.provider.Settings
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skylight.android.volunteering.BuildConfig
import com.skylight.android.volunteering.app.listerners.TryAgainClickListener
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.core.ui.base.BaseActivity
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.app.util.ConnectionType

/**
 * kotlin extension functions for views to make life easy and easy to read
 */

fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.setVisibilityWithGONE(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        gone()
}

fun View.setVisibilityWithGONE(isVisible: Int) {
    if (isVisible == 1)
        visible()
    else
        gone()
}

fun View.setVisibilityWithINVISIBLE(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        invisible()
}

fun View.invisibleWithOutCheck() {
        visibility = View.INVISIBLE
}
fun View.goneWithOutCheck() {
        visibility = View.GONE
}

fun View.visibleWithOutCheck() {
        visibility = View.VISIBLE
}

fun ImageView.setImageFromRes(resourceId: Int) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context, resourceId
        )
    )
}

fun BaseFragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Fragment.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(requireContext(), colorRes)

/**
 * Easy toast function for Activity.
 */
fun FragmentActivity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * Inflate the layout specified by [layoutRes].
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Context.getDrawableCompat(@DrawableRes resId: Int, @ColorRes tintColorRes: Int = 0) = when {
    tintColorRes != 0 -> AppCompatResources.getDrawable(this, resId)?.apply {
        setColorFilter(getColorCompat(tintColorRes), PorterDuff.Mode.SRC_ATOP)
    }
    else -> AppCompatResources.getDrawable(this, resId)
}!!

/*fun CoroutineScope.isInternetWorking(application: Application) = produce {
    val response = GoogleServiceModule.provideGoogleService(application).getGoogle()
    send(response.isSuccessful)
}*/

inline fun <reified T : Activity> Activity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}

fun BaseFragment.showTryAgainSnackBar(
    view: View,
    msg: String,buttonTitle: String,
    tryAgainClickListener: TryAgainClickListener
) {
    val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
    (snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).maxLines = 4
    snackbar.setAction(buttonTitle) {
        tryAgainClickListener.onTryAgainClick()
        snackbar.dismiss()
    }
    snackbar.show()
}

fun BaseActivity.showLongToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun BaseFragment.showLongSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

fun BaseFragment.showShortSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()

fun BaseFragment.showLongToast(msg: String) =
    Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()

fun BaseFragment.showShortToast(msg: String) =
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

fun Resources.isInLandScapeMode(): Boolean =
    this.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            if (editable.toString().isNotEmpty() /*&& (editable.toString().length % 2) == 0*/)
                afterTextChanged.invoke(editable.toString())
        }
    })
}


fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this == 1

fun Float.roundTo(n: Int): Float {
    return "%.${n}f".format(this).toFloat()
}

fun String.returnEmptyStringIfStringIsNullOrBlank(string: String): String {
    return if (string.isNullOrEmpty()) "" else string
}

fun String?.returnEmptyStringIfStringIsNullOrBlankNullable(string: String?): String {
    return if (string.isNullOrEmpty()) "" else string
}

fun String.formatUserNameInitials(userName: String?): String {
    val nameParts: List<String> = userName!!.split(" ")
    val firstName = nameParts[0]
    val firstNameChar = firstName[0]

    return if (nameParts.size > 1) {
        val lastName = nameParts[nameParts.size - 1]
        val lastNameChar = lastName[0]
        (firstNameChar.plus(" ".plus(lastNameChar))).toUpperCase()
    } else {
        firstNameChar.toUpperCase().toString()
    }
}

fun String.isInternetError(): Int {
    if (this == MConstants.TRY_AGAIN) {
        return MConstants.WENT_WRONG_ID
    } else if (this == MConstants.NOT_CONNECTED_TO_INTERNET || this == MConstants.NO_INTERNET) {
        return MConstants.NETWORK_ERROR__ID
    } else {
        return MConstants.BACKEND_ERROR__ID
    }
}

fun getDeviceUniqueId(context: Context?): String = Settings.Secure.getString(
    context?.getContentResolver(),
    Settings.Secure.ANDROID_ID
) //UUID.randomUUID().toString()

fun getAppVersionName(): String = BuildConfig.VERSION_NAME

fun getConnectionType(context: Context): ConnectionType {
    var result: ConnectionType =
        ConnectionType.NONE // Returns connection type. 0: none; 1: mobile data; 2: wifi
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = ConnectionType.WIFI
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = ConnectionType.MOBILE
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = ConnectionType.WIFI
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = ConnectionType.MOBILE
                }
            }
        }
    }
    return result
}

fun AppCompatActivity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    } else {
        window.setDecorFitsSystemWindows(false)
    }
}

fun <T : Any> BaseFragment.setBackStackData(key: String,data : T, doBack : Boolean = true) {
    findNavController(this).previousBackStackEntry?.savedStateHandle?.set(key, data)
    if(doBack)
        findNavController(this).popBackStack()
}

fun <T : Any> BaseFragment.getBackStackData(key: String, result: (T) -> (Unit)) {
    findNavController(this).currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        result(it)
    }
}