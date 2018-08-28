package zalora.twitsplit.ui.message

import android.text.TextUtils

import java.util.ArrayList

object MessageInteractor {
    val MESSAGE_MAX_LENGTH = 50
    val EXTRA_PREF_LENGTH = 4

    interface OnFinishedListener {
        fun onFinished(items: Array<String>)
        fun onError(error: String)
    }

    private fun isValid(message: String): Boolean {
        if (TextUtils.isEmpty(message)) {
            return false
        }

        if (message.length < MESSAGE_MAX_LENGTH) return true

        val chunks = message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (chunk in chunks) {
            if (chunk.length >= MESSAGE_MAX_LENGTH - EXTRA_PREF_LENGTH) return false
        }
        return true
    }


    fun splitMessage(message: String, listener: OnFinishedListener) {
        try {
            val result = splitMessage(message)
            listener.onFinished(result)
        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError(e.message!!)
        }

    }


    /**
     * Split message to chunks
     * @param message
     * @return chunks
     * @throws Exception
     */
    @Throws(Exception::class)
    fun splitMessage(message: String): Array<String> {
        if (TextUtils.isEmpty(message)) {
            throw Exception("The message is empty")
        }
        if (!isValid(message)) {
            throw Exception("the message contains a span of non-whitespace characters longer than 50 characters")
        }
        if (message.length < MESSAGE_MAX_LENGTH) {
            return arrayOf(message)
        }

        // Todo: split and return
        val msgChunks = ArrayList<String>()

        val chunks = message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var temp = StringBuilder()
        for (i in chunks.indices) {
            val chunk = chunks[i]

            if (temp.length + chunk.length > MESSAGE_MAX_LENGTH - EXTRA_PREF_LENGTH) {
                msgChunks.add(temp.toString())
                temp = StringBuilder()
            }


            if (temp.length != 0) {
                temp.append(" ")
            }
            temp.append(chunk)
        }
        //Add last index chunk
        if (temp.length != 0) {
            msgChunks.add(temp.toString())
        }

        val results = Array(msgChunks.size, {i -> (String.format("%d/%d %s", i + 1, msgChunks.size, msgChunks[i])) })
        return results
    }
}
