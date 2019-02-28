package me.l3m4rk.test.presentation.common

import android.content.Context
import com.google.common.truth.Truth.assertThat
import me.l3m4rk.test.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.net.UnknownHostException

private const val DEFAULT_ERROR_MESSAGE = "Something went wrong!"
private const val NO_CONNECTION_ERROR_MESSAGE = "No connection!"

@RunWith(MockitoJUnitRunner::class)
class ErrorMessageFactoryTest {

    private lateinit var messageFactory: ErrorMessageFactory

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        Mockito.`when`(context.getString(R.string.error_no_connection))
            .thenReturn(NO_CONNECTION_ERROR_MESSAGE)
        Mockito.`when`(context.getString(R.string.error_default))
            .thenReturn(DEFAULT_ERROR_MESSAGE)
        messageFactory = ErrorMessageFactory(context)
    }

    @Test
    fun getUnknownHostException__shouldReturnNoConnection() {

        val ex = UnknownHostException()

        assertThat(messageFactory.createMessage(ex))
            .isEqualTo(NO_CONNECTION_ERROR_MESSAGE)
    }

    @Test
    fun getIOException__shouldReturnDefaultMessage() {

        val ex = IOException()

        assertThat(messageFactory.createMessage(ex))
            .isEqualTo(DEFAULT_ERROR_MESSAGE)
    }

    @Test
    fun getNumberFormatException__shouldReturnDefaultMessage() {

        val ex = NumberFormatException()

        assertThat(messageFactory.createMessage(ex))
            .isEqualTo(DEFAULT_ERROR_MESSAGE)
    }


}