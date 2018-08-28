package zalora.twitsplit.ui.message

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import zalora.twitsplit.R
import zalora.twitsplit.base.BaseActivity
import zalora.twitsplit.base.BaseFragment
import zalora.twitsplit.data.room.entity.MessageModel

class HistoryMessageFragment : BaseFragment(), BaseActivity.FragmentBackListener, HistoryMessageView {
    private var llEmpty: View? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var historyAdapter: HistoryMessageAdapter? = null
    private var presenter: HistoryMessagePresenter? = null

    private val mRecycleListener = RecyclerView.RecyclerListener {
        //Todo : recycled data
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_message, container, false)
        (activity as BaseActivity).fragmentBackListener = this

        mRecyclerView = view.findViewById(R.id.recycler_view)
        llEmpty = view.findViewById(R.id.ll_empty)
        mLinearLayoutManager = LinearLayoutManager(activity)

        historyAdapter = HistoryMessageAdapter(activity!!)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = mLinearLayoutManager
        mRecyclerView!!.adapter = historyAdapter
        mRecyclerView!!.setRecyclerListener(mRecycleListener)
        val itemDecor = DividerItemDecoration(activity!!, VERTICAL)
        mRecyclerView!!.addItemDecoration(itemDecor)

        val btnAddRecord = view.findViewById<View>(R.id.btn_new_record)
        btnAddRecord.setOnClickListener { callAddNewMessage() }
        showEmptyView()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HistoryMessagePresenter(this)
        title = (activity as MessageActivity).setActionBarTitle(R.string.title_history)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.loadData(context!!)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    private fun showEmptyView() {
        if (historyAdapter != null && historyAdapter!!.itemCount > 0) {
            llEmpty!!.visibility = View.GONE
            mRecyclerView!!.visibility = View.VISIBLE
        } else {
            llEmpty!!.visibility = View.VISIBLE
            mRecyclerView!!.visibility = View.GONE
        }
    }

    override fun onFragmentBackPressed(): Boolean {
        val builder = AlertDialog.Builder(activity!!)
                .setMessage("Are you want to exit?")
                .setPositiveButton("Ok") { _, _ -> System.exit(0) }
                .setNegativeButton("Cancel") { _, _ ->
                    //Do nothing
                }
        builder.create().show()
        return false
    }

    override fun showProgress() {
        (activity as BaseActivity).showLoadingDialog()
    }

    override fun hideProgress() {
        (activity as BaseActivity).hideLoadingDialog()
    }

    override fun callAddNewMessage() {
        (activity as MessageActivity).changeToNewMessageView()
    }

    override fun onLoadedData(datas: List<MessageModel>) {
        historyAdapter!!.initData(datas)
        if (datas.size != 0) {
            mRecyclerView!!.scrollToPosition(datas.size - 1)
        }

        showEmptyView()
    }

    override fun onError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}
