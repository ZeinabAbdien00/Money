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

        return dummyFriendData
    }
}