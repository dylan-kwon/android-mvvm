package dylan.kwon.mvvm.ui.screen.viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.ActivityViewpagerBinding
import dylan.kwon.mvvm.util.base.activity.BaseActivity
import dylan.kwon.mvvm.util.extension.observeBaseViewModelEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewPagerActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, ViewPagerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }

        fun intent(context: Context) = createIntent(context).let {
            context.startActivity(it)
        }
    }

    private val binding: ActivityViewpagerBinding by lazy {
        DataBindingUtil.setContentView<ActivityViewpagerBinding>(this, R.layout.activity_viewpager)
    }

    private val viewModel: ViewPagerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        initUsers()
        viewModel.requestGetUsers()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        observeBaseViewModelEvent(viewModel)
    }

    private fun initUsers() {
        binding.users.orientation =
            ViewPager2.ORIENTATION_HORIZONTAL

        binding.users.adapter =
            ViewPagerAdapter(this, viewModel)
    }

}