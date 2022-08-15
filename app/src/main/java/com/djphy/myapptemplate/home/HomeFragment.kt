package com.djphy.myapptemplate.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.djphy.myapptemplate.R
import com.djphy.myapptemplate.base.BaseFragment
import com.djphy.myapptemplate.extension.createFactory
import com.djphy.myapptemplate.databinding.FragmentHomeBinding
import com.djphy.myapptemplate.home.HomeState.HomeFragmentState.*
import com.djphy.myapptemplate.home.adapter.MyHomeArticleAdapter
import com.djphy.myapptemplate.home.model.UserAndLaunches

class HomeFragment : BaseFragment(), MyHomeArticleAdapter.SelectionListener {

    private lateinit var mViewModel: HomeViewModel
    private lateinit var mViewBinding: FragmentHomeBinding
    private var mAdapter: MyHomeArticleAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postEventsToViewModel(InitState)
    }

    private fun initVm() {
        //use activity store for vm
        val factory = HomeViewModel(requireActivity().application).createFactory()
        mViewModel = ViewModelProvider(requireActivity(), factory)
            .get(HomeViewModel::class.java)
        mViewModel.mStateObservable.observe(this) {
            mViewBinding.state = it
            updateView(it)
        }
    }


    private fun updateView(state: HomeState) {
        when (state) {
            InitState -> {
                init()
            }

            is FetchUserLaunchListResponseState -> {
                postEventsToViewModel(HomeState.LoadingState(false))
                if (state.isSuccess) {
                    val itemList = arrayListOf<UserAndLaunches>()
                    state.data?.also {
                        for (i in 0 until it.users.size){
                            val item = UserAndLaunches(it.users[i], it.launches?.get(i))
                            itemList.add(item)
                        }
                        mAdapter?.submitList(itemList)
                    }
                } else {
                    //unable to apply changes, try again later
                    val message = state.message ?: requireActivity().getString(
                        R.string.generic_error
                    )
                    postEventsToViewModel(InitLoadFailedState(message))
                }
            }

            else -> {
                //do nothing
            }
        }

    }

    private fun init() {
        initToolBar()
        initRecyclerView()
        postEventsToViewModel(FetchUserLaunchListState)
    }

    private fun initToolBar() {
        mViewBinding.includeToolbar.apply {
            tvTitle.text = getString(R.string.title)
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun initRecyclerView() {
        mViewBinding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            mAdapter = MyHomeArticleAdapter(
                this@HomeFragment
            )
            adapter = mAdapter
        }
    }

    override fun onItemClick(position: Int, item: UserAndLaunches) {
        item.launches?.links?.also {
            postEventsToViewModel(
                ArticleItemClickState(it, item.user.name!!)
            )
        }
    }

    private fun postEventsToViewModel(state: HomeState) {
        mViewModel.nextState(state)
    }

}