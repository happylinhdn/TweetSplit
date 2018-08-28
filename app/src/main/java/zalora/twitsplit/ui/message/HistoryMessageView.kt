package zalora.twitsplit.ui.message


import zalora.twitsplit.data.room.entity.MessageModel

interface HistoryMessageView {

    fun showProgress()

    fun hideProgress()

    fun callAddNewMessage()

    fun onLoadedData(datas: List<MessageModel>)

    fun onError(error: String)
}