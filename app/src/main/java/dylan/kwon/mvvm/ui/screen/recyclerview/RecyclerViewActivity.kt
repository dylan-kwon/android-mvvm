package dylan.kwon.mvvm.ui.screen.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.ActivityRecyclerviewBinding
import dylan.kwon.mvvm.util.base.activity.BaseActivity
import dylan.kwon.mvvm.util.extension.observeBaseViewModelEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecyclerViewActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, RecyclerViewActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        fun intent(context: Context) = createIntent(context).let {
            context.startActivity(it)
        }
    }

    private val binding: ActivityRecyclerviewBinding by lazy {
        DataBindingUtil.setContentView<ActivityRecyclerviewBinding>(this, R.layout.activity_recyclerview)
    }

    private val viewModel: RecyclerViewModel by viewModel()

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
        binding.users.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.users.adapter = RecyclerViewAdapter(this, viewModel)
    }

}