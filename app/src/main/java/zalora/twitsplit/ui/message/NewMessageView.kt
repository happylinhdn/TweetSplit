package zalora.twitsplit.ui.message


interface NewMessageView {

    fun showProgress()

    fun hideProgress()

    fun setItems(items: Array<String>)

    fun showError(message: String)
}