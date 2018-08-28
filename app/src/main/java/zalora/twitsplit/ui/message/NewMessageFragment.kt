package zalora.twitsplit.ui.message

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import zalora.twitsplit.R
import zalora.twitsplit.base.BaseActivity
import zalora.twitsplit.base.BaseFragment
import zalora.twitsplit.utils.CommonUtils

class NewMessageFragment : BaseFragment(), NewMessageView, TextWatcher, View.OnClickListener {
    private var presenter: NewMessagePresenter? = null
    private var edtInput: EditText? = null
    private var grpInput: TextInputLayout? = null
    private var btnSend: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_message, container, false)
        edtInput = view.findViewById(R.id.edt_input)
        grpInput = view.findViewById(R.id.grp_input)
        btnSend = view.findViewById(R.id.btn_send)
        btnSend!!.setOnClickListener(this)

        presenter = NewMessagePresenter(this)

        edtInput!!.addTextChangedListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter!!.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    override
    fun showProgress() {
        (activity as BaseActivity).showLoadingDialog()
    }

    override
    fun hideProgress() {
        (activity as BaseActivity).hideLoadingDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = (activity as MessageActivity).setActionBarTitle("New Message")
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        if (TextUtils.isEmpty(s)) {
            btnSend!!.isEnabled = false
        } else {
            btnSend!!.isEnabled = true
        }
    }

    override fun onClick(v: View) {
        val vId = v.id
        when (vId) {
            R.id.btn_send -> presenter!!.sendMessage(edtInput!!.text.toString().trim { it <= ' ' })
        }
    }

    override
    fun setItems(items: Array<String>) {
        presenter!!.saveMessageToDb(context!!, items)
        Toast.makeText(context, "The message has sent", Toast.LENGTH_LONG).show()
        CommonUtils.hideSoftKeyboard(activity!!)
        activity!!.onBackPressed()
    }

    override
    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        edtInput!!.error = message
    }
}
