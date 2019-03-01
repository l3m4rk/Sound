package me.l3m4rk.test.presentation.common

import android.content.Context
import me.l3m4rk.test.R
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMessageFactory
@Inject constructor(
    private val context: Context
) {

    fun createMessage(t: Throwable): String {
        return when (t) {
            is UnknownHostException -> context.getString(R.string.error_no_connection)
            is IllegalStateException -> t.message!!
            else -> context.getString(R.string.error_default)
        }
    }

}