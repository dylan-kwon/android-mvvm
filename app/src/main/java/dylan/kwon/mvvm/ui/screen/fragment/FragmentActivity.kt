package dylan.kwon.mvvm.ui.screen.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.ActivityFragmentBinding
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.base.activity.BaseActivity

class FragmentActivity : BaseActivity(), Interaction {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, FragmentActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        fun intent(context: Context) = createIntent(context).let {
            context.startActivity(it)
        }
    }

    private val binding: ActivityFragmentBinding by lazy {
        DataBindingUtil.setContentView<ActivityFragmentBinding>(this, R.layout.activity_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showChildFragment(savedInstanceState)
    }

    private fun showChildFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }
        ChildFragment.newInstance()
            .add(supportFragmentManager, binding.container, ChildFragment.TAG)
    }

    override fun onInitializedChildFragment() {
        LogUtil.d(tag, "onInitializedChildFragment")
    }

}