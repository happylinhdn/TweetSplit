package zalora.twitsplit.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import zalora.twitsplit.base.BaseActivity
import zalora.twitsplit.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*


object CommonUtils {
    private val TAG = "CommonUtils"

    val timeStamp: String
        get() = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())

    val current: Date
        get() = Calendar.getInstance(Locale.US).time

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    fun resetFragmentTitle(fragments: List<Fragment>?, activity: BaseActivity?) {
        if (activity == null)
            return
        if (fragments != null) {
            for (fragmentTemp in fragments) {
                if (fragmentTemp.isVisible) {
                    if (fragmentTemp is BaseFragment) {
                        activity.setActionBarTitle(fragmentTemp.title!!)
                    }
                }
            }
        }
    }

    fun getLongStringFrom(date: Date): String {
        return SimpleDateFormat(AppConstants.FULL_TIMESTAMP_FORMAT, Locale.US).format(date)
    }

    fun getShortStringFrom(date: Date): String {
        return SimpleDateFormat(AppConstants.SHORT_TIMESTAMP_FORMAT, Locale.US).format(date)
    }
}
