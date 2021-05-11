package com.neo.testtutorial

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * This class was created because there is no way to run suspend functions
 * ..in JUnit test because the main Looper is not available. So this class
 * ..provides a Looper for main for suspend functions to occur
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule (
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    /**
     * When we start a coroutine, we choose Dispatchers.setMain() to set a
     * ..particular dispatcher to be used
     */
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    /**
     * Here we want to clean up the test coroutines, then we set the main
     * ..Dispatcher to the actual main Dispatcher
     */
    override fun finished(description: Description?) {
        super.finished(description)

        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}