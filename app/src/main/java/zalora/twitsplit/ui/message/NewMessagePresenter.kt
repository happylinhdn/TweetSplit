package zalora.twitsplit.ui.message

import android.content.Context
import android.os.Handler
import zalora.twitsplit.data.room.entity.MessageModel
import zalora.twitsplit.data.room.repository.MessageRepository
import zalora.twitsplit.utils.AppConstants
import zalora.twitsplit.utils.CommonUtils

class NewMessagePresenter internal constructor(private var newMessageView: NewMessageView?) {

    internal fun onResume() {

    }

    internal fun sendMessage(message: String) {
        if (newMessageView != null) {
            newMessageView!!.showProgress()
        }
        Handler().post {
            MessageInteractor.splitMessage(message, object : MessageInteractor.OnFinishedListener {
                override fun onFinished(items: Array<String>) {
                    if (newMessageView != null) {
                        newMessageView!!.setItems(items)
                        newMessageView!!.hideProgress()
                    }
                }

                override fun onError(error: String) {
                    if (newMessageView != null) {
                        newMessageView!!.showError(error)
                        newMessageView!!.hideProgress()
                    }
                }
            })
        }


    }

    internal fun onDestroy() {
        newMessageView = null
    }

    fun saveMessageToDb(context: Context, messages: Array<String>?) {
        if (messages == null || messages.size == 0) return
        val hashGroupKey = AppConstants.PREF_NAME + "_" + CommonUtils.timeStamp
        val messageModels = Array(messages.size, { i -> MessageModel(messages[i], CommonUtils.current.time, hashGroupKey)})
        val messageRepository = MessageRepository.getInstance(context.applicationContext)
        messageRepository.insertNewMessages(*messageModels)
    }
}
