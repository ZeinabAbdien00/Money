package com.iti.moneyapp.ui.home.dashboard.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iti.moneyapp.adapter.home.HomeRVAdapter
import com.iti.moneyapp.databinding.FragmentIncomeBinding
import com.iti.moneyapp.model.db.HomeModel

class IncomeFragment : Fragment() {

    private lateinit var binding: FragmentIncomeBinding
    private lateinit var newsAdapter: HomeRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
    }

    private fun setupRV() {
        newsAdapter = HomeRVAdapter()
        newsAdapter.differ.submitList(setUpFriendsArrayList().toList())
        binding.incomeLayout.rvItemHome.apply {
            adapter = newsAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun setUpFriendsArrayList(): ArrayList<HomeModel> {
        val dummyFriendData = ArrayList<HomeModel>()
        dummyFriendData.add(0, HomeModel(0, "Sports Suzan Suzan Suzan", 200))
        dummyFriendData.add(1, HomeModel(1, "Sports1", 200))
        dummyFriendData.add(2, HomeModel(2, "Sports2", 200))
        dummyFriendData.add(3, HomeModel(3, "Sports3", 200))
        dummyFriendData.add(4, HomeModel(4, "Sports4", 200))
        dummyFriendData.add(5, HomeModel(5, "Sports5", 200))
        dummyFriendData.add(6, HomeModel(6, "Sports6", 200))
        dummyFriendData.add(7, HomeModel(7, "Sports7", 200))
        dummyFriendData.add(8, HomeModel(8, "Sports8", 200))
        dummyFriendData.add(9, HomeModel(9, "Sports9", 200))

        return dummyFriendData
    }
}