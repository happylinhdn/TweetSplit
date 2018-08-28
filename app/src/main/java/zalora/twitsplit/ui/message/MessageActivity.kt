package zalora.twitsplit.ui.message

import zalora.twitsplit.R
import zalora.twitsplit.base.BaseActivity

class MessageActivity : BaseActivity() {
    override fun initFragment() {
        val tx = supportFragmentManager.beginTransaction()
        val fragment = HistoryMessageFragment()
        tx.replace(R.id.activity_template_frame, fragment)
        tx.commit()
    }

    fun changeToNewMessageView() {
        val fragment = NewMessageFragment()
        super.displaySelectedScreen(fragment, 1)
    }
}
