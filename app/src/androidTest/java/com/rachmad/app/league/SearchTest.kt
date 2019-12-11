package com.rachmad.app.league

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.helper.EspressoIdlingResource
import com.rachmad.app.league.ui.search.SearchMatchActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class SearchTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityRule = ActivityTestRule(SearchMatchActivity::class.java)

    @Before
    fun initial(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @Test
    fun useAppContext() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.rachmad.app.league", appContext.packageName)

        onView(allOf(withId(R.id.search_text))).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Arsenal"), pressImeActionButton())

        isRecyclerViewHaveItem(R.id.search_match_list)
//---------------------------------------------------------------------------------------------------------------------------

        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Liverpool"), pressImeActionButton())

        isRecyclerViewHaveItem(R.id.search_match_list)
//---------------------------------------------------------------------------------------------------------------------------

        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Chelsea"), pressImeActionButton())

        isRecyclerViewHaveItem(R.id.search_match_list)
//---------------------------------------------------------------------------------------------------------------------------
    }

    @After
    fun end(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }

    fun isRecyclerViewHaveItem(@IdRes RecyclerViewId: Int) {
        val matcher = object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                Log.d("MOVIE", "ITEM COUNT : " + (item as RecyclerView).adapter!!.itemCount)
                return (item as RecyclerView).adapter!!.itemCount > 0
            }

            override fun describeTo(description: Description) {}
        }
        onView(allOf(withId(RecyclerViewId),
            ViewMatchers.isDisplayed()
        )).check(ViewAssertions.matches(matcher))
    }
}
