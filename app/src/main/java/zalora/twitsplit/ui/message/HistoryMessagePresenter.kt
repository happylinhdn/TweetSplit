package zalora.twitsplit.ui.message

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import zalora.twitsplit.data.room.repository.MessageRepository

class HistoryMessagePresenter(private var historyMessageView: HistoryMessageView?) {

    fun loadData(context: Context) {
        if (historyMessageView != null) {
            historyMessageView!!.showProgress()
        }

        val messageRepository = MessageRepository.getInstance(context.applicationContext)
        messageRepository.allMessage
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ messageModels ->
                    if (historyMessageView != null) {
                        historyMessageView!!.onLoadedData(messageModels)
                        historyMessageView!!.hideProgress()
                    }
                }) { throwable ->
                    if (historyMessageView != null) {
                        historyMessageView!!.onError(throwable.message!!)
                        historyMessageView!!.hideProgress()
                    }
                }
    }

    fun onDestroy() {
        historyMessageView = null
    }
}
