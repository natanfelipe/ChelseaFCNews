package com.br.natanfelipe.chelseafcnews.ui.list

import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.br.natanfelipe.chelseafcnews.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.util.EspressoIdlingResources
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    val LIST_ITEM_CLICKED = 3
    val MIN_ITEM_COUNT = 20
    val IS_EMPTY = ""
    val READ_MORE = "Tap to read more"

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun is_newsList_visible_on_app_launch() {
        onView(withId(R.id.newsList)).check(matches(isDisplayed()))
    }

    @Test
    fun is_newsList_with_minimun_number_of_articles() {
        onView(withId(R.id.newsList)).check(matches(hasMinimumChildCount(MIN_ITEM_COUNT)))
    }

    @Test
    fun select_list_item_displays_article_details_with_title_not_blank() {
        onView(withId(R.id.newsList)).check(matches(isDisplayed()))

        onView(withId(R.id.newsList)).perform(
            actionOnItemAtPosition<HomeViewHolder>(
                LIST_ITEM_CLICKED,
                click()
            )
        )

        onView(withId(R.id.newsTitle)).check(matches(not(withText(IS_EMPTY))))
    }

    @Test
    fun navigate_to_article_details_with_title_not_blank_and_go_back_to_home_fragment(){
        onView(withId(R.id.newsList)).check(matches(isDisplayed()))

        onView(withId(R.id.newsList)).perform(
            actionOnItemAtPosition<HomeViewHolder>(
                LIST_ITEM_CLICKED,
                click()
            )
        )

        onView(withId(R.id.newsTitle)).check(matches(not(withText(IS_EMPTY))))

        pressBack()

        onView(withId(R.id.newsList)).check(matches(isDisplayed()))
    }

    @Test
    fun navigate_to_article_details_with_title_not_blank_and_navigate_to_webview(){
        onView(withId(R.id.newsList)).check(matches(isDisplayed()))

        onView(withId(R.id.newsList)).perform(
            actionOnItemAtPosition<HomeViewHolder>(
                LIST_ITEM_CLICKED,
                click()
            )
        )

        onView(withId(R.id.newsTitle)).check(matches(not(withText(IS_EMPTY))))

        onView(withId(R.id.newsBody)).perform(clickClickableSpan(READ_MORE))

        onView(withId(R.id.webview)).check(matches(isDisplayed()))
    }

    fun clickClickableSpan(textToClick: CharSequence): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return Matchers.instanceOf(TextView::class.java)
            }

            override fun getDescription(): String {
                return "clicking on a ClickableSpan";
            }

            override fun perform(uiController: UiController, view: View) {
                val textView = view as AppCompatTextView
                val spannableString = textView.text as SpannableString

                if (spannableString.isEmpty()) {
                    throw NoMatchingViewException.Builder()
                        .includeViewHierarchy(true)
                        .withRootView(textView)
                        .build();
                }

                val spans = spannableString.getSpans(0, spannableString.length, ClickableSpan::class.java)
                if (spans.isNotEmpty()) {
                    var spanCandidate: ClickableSpan
                    for (span: ClickableSpan in spans) {
                        spanCandidate = span
                        val start = spannableString.getSpanStart(spanCandidate)
                        val end = spannableString.getSpanEnd(spanCandidate)
                        val sequence = spannableString.subSequence(start, end)
                        if (textToClick.toString().equals(sequence.toString())) {
                            span.onClick(textView)
                            return;
                        }
                    }
                }

                // textToClick not found in TextView
                throw NoMatchingViewException.Builder()
                    .includeViewHierarchy(true)
                    .withRootView(textView)
                    .build()

            }
        }
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }

}