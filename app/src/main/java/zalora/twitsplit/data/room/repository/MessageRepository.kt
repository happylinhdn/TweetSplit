package zalora.twitsplit.data.room.repository

import android.content.Context
import android.os.AsyncTask

import io.reactivex.Flowable
import io.reactivex.Single
import zalora.twitsplit.data.room.MyDatabase
import zalora.twitsplit.data.room.dao.MessageDao
import zalora.twitsplit.data.room.entity.MessageModel

class MessageRepository private constructor(context: Context) {

    private val messageDao: MessageDao

    val lastMessage: Single<MessageModel>
        get() = messageDao.lastMessage

    val allMessage: Flowable<List<MessageModel>>
        get() = messageDao.allMessage

    init {
        val db = MyDatabase.getDatabase(context)
        messageDao = db.messageDao()
    }

    fun insertNewMessages(vararg messageModels: MessageModel) {
        val task = AgentAsyncTask()
        task.execute(*messageModels)
    }

    fun updateOrCreateMessage(messageModel: MessageModel) {
        val task = AgentAsyncTask()
        task.execute(messageModel)
    }

    internal inner class AgentAsyncTask : AsyncTask<MessageModel, Void, Void>() {

        override fun doInBackground(vararg data: MessageModel): Void? {
            messageDao.insertMessage(*data)
            return null
        }
    }

    interface LoadCallback {
        fun onLoaded()
    }

    companion object {
        private var INSTANCE: MessageRepository? = null
        fun getInstance(context: Context): MessageRepository {
            if (INSTANCE == null) {
                synchronized(MessageRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MessageRepository(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
