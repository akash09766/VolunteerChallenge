package com.skylight.android.volunteering.app.fragments

import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 25-02-2022 at 20:08.
 */

/**
 * Source of code -> https://stackoverflow.com/questions/29250506/espresso-how-to-check-if-one-of-the-view-is-displayed
 */
fun ViewInteraction.isGone() = getViewAssertion(ViewMatchers.Visibility.GONE)

fun ViewInteraction.isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

fun ViewInteraction.isInvisible() = getViewAssertion(ViewMatchers.Visibility.INVISIBLE)

private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? {
    return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))
}

/**
 * source -> https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
 */
private class ViewPropertyChangeCallback(
    private val matcher: Matcher<View>,
    private val view: View
) : IdlingResource, ViewTreeObserver.OnDrawListener {

    private lateinit var callback: IdlingResource.ResourceCallback
    private var matched = false

    override fun getName() = "View property change callback"

    override fun isIdleNow() = matched

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun onDraw() {
        matched = matcher.matches(view)
        callback.onTransitionToIdle()
    }
}

fun waitUntil(matcher: Matcher<View>): ViewAction = object : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return any(View::class.java)
    }

    override fun getDescription(): String {
        return StringDescription().let {
            matcher.describeTo(it)
            "wait until: $it"
        }
    }

    override fun perform(uiController: UiController, view: View) {
        if (!matcher.matches(view)) {
            ViewPropertyChangeCallback(matcher, view).run {
                try {
                    IdlingRegistry.getInstance().register(this)
                    view.viewTreeObserver.addOnDrawListener(this)
                    uiController.loopMainThreadUntilIdle()
                } finally {
                    view.viewTreeObserver.removeOnDrawListener(this)
                    IdlingRegistry.getInstance().unregister(this)
                }
            }
        }
    }
}

/**
 * source -> https://github.com/cdmunoz/NasaRoverPhotos
 */
object EspressoTestsHelpers {
    fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }
}