package com.rachmad.app.league

import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object Assertion {
    fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int, count: Int) {
        val matcher = object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                Log.d("MOVIE", "ITEM COUNT : " + (item as RecyclerView).adapter!!.itemCount)
                return (item as RecyclerView).adapter!!.itemCount == count
            }

            override fun describeTo(description: Description) {}
        }
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher))
    }

    fun atPositionOnView(position: Int, itemMatcher: Matcher<View>, targetViewId: Int): Matcher<View> {

        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position $position")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                val targetView = viewHolder?.itemView?.findViewById<View>(targetViewId)
                return itemMatcher.matches(targetView)
            }
        }
    }
}