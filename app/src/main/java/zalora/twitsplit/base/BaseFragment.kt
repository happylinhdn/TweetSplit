package zalora.twitsplit.base

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {
    var title: String? = null
    var customTag = ""
}