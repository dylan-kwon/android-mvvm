package dylan.kwon.mvvm.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.ActivityMainBinding
import dylan.kwon.mvvm.ui.screen.fragment.FragmentActivity
import dylan.kwon.mvvm.ui.screen.recyclerview.RecyclerViewActivity
import dylan.kwon.mvvm.ui.screen.viewpager.ViewPagerActivity
import dylan.kwon.mvvm.util.base.activity.BaseActivity
import dylan.kwon.mvvm.util.extension.observeBaseViewModelEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        fun intent(context: Context) = createIntent(context).let {
            context.startActivity(it)
        }
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        viewModel.intentRecyclerView.observe(this, Observer {
            RecyclerViewActivity.intent(this)
        })
        viewModel.intentViewPager.observe(this, Observer {
            ViewPagerActivity.intent(this)
        })
        viewModel.intentFragment.observe(this, Observer {
            FragmentActivity.intent(this)
        })
        observeBaseViewModelEvent(viewModel)
    }

}