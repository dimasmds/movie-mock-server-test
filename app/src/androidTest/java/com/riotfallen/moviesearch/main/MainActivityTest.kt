package com.riotfallen.moviesearch.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.riotfallen.moviesearch.R
import com.riotfallen.moviesearch.network.MovieDataSource
import com.riotfallen.moviesearch.utils.*
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
    private lateinit var mockResponseUnauthorized: MockResponse
    private lateinit var mockResponseGenericError: MockResponse

    private val mockWebServer = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {

        mockWebServer.start()
        MovieDataSource.baseUrl = mockWebServer.url("/").toString()

        mockResponseSuccess =
            context.createResponse(localResponsePath = "response_success.json", responseCode = 200)

//        you can use this to
//        mockResponseSuccess = MockResponse().apply {
//            setBody(JSONReader.read(context, "response_success.json"))
//            setResponseCode(200)
//        }

        mockResponseEmpty = context.createResponse(
            localResponsePath = "response_success_empty.json",
            responseCode = 200
        )
        mockResponseUnauthorized = context.createResponse(responseCode = 401)
        mockResponseGenericError = context.createResponse(responseCode = 403)

//        you can use this to
//        mockResponseGenericError = MockResponse().apply {
//            setResponseCode(403)
//            setBody("Unknown error")
//        }
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

    @Test
    fun test_should_be_success_but_result_is_empty() {
        mockWebServer.enqueue(mockResponseEmpty)

        val query = "Gue Ganteng"
        R.id.searchView.typeText(query)
        R.id.searchButton.click()

        val textExpected = String.format(context.getString(R.string.empty_message), query)

        R.id.errorMessage.isDisplayed()
        R.id.errorMessage.matchesWith(textExpected)
    }

    @Test
    fun test_should_be_failed_because_wrong_api_key() {
        mockWebServer.enqueue(mockResponseUnauthorized)

        R.id.searchView.typeText("Marvel")
        R.id.searchButton.click()

        R.id.errorMessage.isDisplayed()
        R.id.errorMessage.matchesWith(
            String.format(
                context.getString(R.string.error_message),
                "Unauthorized"
            )
        )
    }

    @Test
    fun test_should_be_failed_because_unknown_error() {
        mockWebServer.enqueue(mockResponseGenericError)

        R.id.searchView.typeText("Marvel")
        R.id.searchButton.click()

        R.id.errorMessage.isDisplayed()
        R.id.errorMessage.matchesWith(
            String.format(
                context.getString(R.string.error_message),
                "Unknown error"
            )
        )
    }

    @Test
    fun test_should_be_failed_because_query_is_empty() {
        R.id.searchButton.click()

        R.id.errorMessage.isDisplayed()
        R.id.errorMessage.matchesWith(
            String.format(
                context.getString(R.string.error_message),
                "Query must not be empty"
            )
        )
    }
}