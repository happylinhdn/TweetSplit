package zalora.twitsplit.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView

import zalora.twitsplit.R
import zalora.twitsplit.utils.CommonUtils

abstract class BaseActivity : AppCompatActivity() {
    var tlrMainToolBar: Toolbar?= null
    var txtTitle: TextView? = null
    var fragmentBackListener: FragmentBackListener? = null
    var progressBar : ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        progressBar = findViewById(R.id.progressbar)
        initActionBar()

        setOrientation()
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        initFragment()
    }

    abstract fun initFragment()

    override fun onBackPressed() {
        if (fragmentBackListener != null) {
            if (fragmentBackListener!!.onFragmentBackPressed()) {
                fragmentBackListener = null
            }
        } else {
            val count = supportFragmentManager.backStackEntryCount

            if (count == 0) {
                super.onBackPressed()
            } else {
                val fragmentManager = supportFragmentManager
                fragmentManager.popBackStack()
                fragmentManager.executePendingTransactions()
                val fragments = fragmentManager.fragments
                CommonUtils.resetFragmentTitle(fragments, this)
            }
        }
    }

    protected fun initActionBar() {
        tlrMainToolBar = findViewById(R.id.activity_template_toolbar)
        setSupportActionBar(tlrMainToolBar)
        var mainToolBar = tlrMainToolBar
        if (mainToolBar != null)
            mainToolBar.navigationIcon = null
        val actionBar = supportActionBar
        actionBar!!.setCustomView(R.layout.actionbar_custom)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setHomeButtonEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(false)

        txtTitle = findViewById(R.id.actionbar_title)
    }

    fun setActionBarTitle(titleID: Int): String {
        val title = getString(titleID)
        return setActionBarTitle(title)
    }

    fun setActionBarTitle(title: String): String {
        txtTitle?.text = title
        return title
    }

    private fun setOrientation() {
        val isTablet = false
        if (isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    fun displaySelectedScreen(fragment: Fragment?, animId: Int) {
        var customTag = ""
        if (fragment is BaseFragment) {
            customTag = fragment.customTag
        }
        if (fragment != null) {
            fragmentBackListener = null
            val ft = supportFragmentManager.beginTransaction()
            if (animId == 1) {
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            } else if (animId == 2) {
                ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
            }
            ft.add(R.id.activity_template_frame, fragment, customTag)
            val fragmentManager = supportFragmentManager
            val fragments = fragmentManager.fragments
            if (fragments != null) {
                for (fragmentTemp in fragments) {
                    if (fragmentTemp != null && fragmentTemp.isVisible) {
                        ft.hide(fragmentTemp)
                    }
                }
            }

            ft.addToBackStack(customTag)
            ft.commit()
        }
    }

    public override fun onResume() {
        super.onResume()
        refreshUI()
    }

    interface FragmentBackListener {
        fun onFragmentBackPressed(): Boolean
    }

    private fun refreshUI() {

    }

    private fun updateViews() {

    }

    fun showLoadingDialog() {
        progressBar!!.visibility = View.VISIBLE
    }

    fun hideLoadingDialog() {
        progressBar!!.visibility = View.GONE
    }

}
