package zalora.twitsplit

import org.junit.After
import org.junit.Test

import zalora.twitsplit.ui.message.MessageInteractor

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals

class MessageTest {
    private var input: String? = null
    private var expectChunks: Array<String>? = null
    private var errorMessage: String? = null

    internal fun setUpTestCase1() {
        println("Setup testcase 1")
        // setup
        input = "This is short message"
        expectChunks = arrayOf("This is short message")
        errorMessage = null
    }

    internal fun setUpTestCase2() {
        println("Setup testcase 2")
        // setup
        input = ""
        expectChunks = arrayOf()
        errorMessage = MessageInteractor.ERROR_EMPTY_MESSAGE
    }

    internal fun setUpTestCase3() {
        println("Setup testcase 3")
        // setup
        input = "============== ========================================================================================================="
        expectChunks = arrayOf()
        errorMessage = MessageInteractor.ERROR_LONG_PHASE_MESSAGE
    }

    internal fun setUpTestCase4() {
        println("Setup testcase 4")
        // setup
        input = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        expectChunks = arrayOf("1/2 I can't believe Tweeter now supports chunking", "2/2 my messages, so I don't have to do it myself.")
        errorMessage = null
    }

    @Test
    fun doTest1() {
        setUpTestCase1()
        println("Do Test")
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input!!))
        } catch (e: Exception) {
            println("Do Test error: " + e.message)
            assertEquals(errorMessage, e.message)
        }

    }

    @Test
    fun doTest2() {
        setUpTestCase2()
        println("Do Test")
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input!!))
        } catch (e: Exception) {
            assertEquals(errorMessage, e.message)
        }

    }

    @Test
    fun doTest3() {
        setUpTestCase3()
        println("Do Test")
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input!!))
        } catch (e: Exception) {
            assertEquals(errorMessage, e.message)
        }

    }

    @Test
    fun doTest4() {
        setUpTestCase4()
        println("Do Test")
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input!!))
        } catch (e: Exception) {
            assertEquals(errorMessage, e.message)
        }

    }

    @After
    fun after() {
        println("Test finished")
    }
}
