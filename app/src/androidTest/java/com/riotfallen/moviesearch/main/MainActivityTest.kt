package com.riotfallen.moviesearch.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.riotfallen.moviesearch.R
import com.riotfallen.moviesearch.network.MovieDataSource
import com.riotfallen.moviesearch.utils.click
import com.riotfallen.moviesearch.utils.createResponse
import com.riotfallen.moviesearch.utils.isDisplayed
import com.riotfallen.moviesearch.utils.typeText
import kotlinx.android.synthetic.main.activity_main.view.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var mockResponseSuccess: MockResponse
    private lateinit var mockResponseEmpty: MockResponse
    private lateinit var mockResponseError: MockResponse

    private val mockWebServer = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {

        mockWebServer.start()
        MovieDataSource.baseUrl = mockWebServer.url("/").toString()

        mockResponseSuccess =
            context.createResponse(localResponsePath = "response_success.json", responseCode = 200)
        mockResponseEmpty = context.createResponse(
            localResponsePath = "response_success_empty.json",
            responseCode = 200
        )
        mockResponseError = context.createResponse(responseCode = 505)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_should_be_success() {
        mockWebServer.enqueue(mockResponseSuccess)

        R.id.searchView.typeText("Marvel")
        R.id.searchButton.click()

        R.id.recyclerView.isDisplayed()
    }
}