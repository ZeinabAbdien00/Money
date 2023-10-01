package com.iti.moneyapp.ui.home.dashboard.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iti.moneyapp.adapter.home.HomeRVAdapter
import com.iti.moneyapp.data.local.home_db.HomeDao
import com.iti.moneyapp.data.local.home_db.HomeDatabase
import com.iti.moneyapp.databinding.FragmentExpenseBinding
import com.iti.moneyapp.model.db.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var newsAdapter: HomeRVAdapter
    private var dao: HomeDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { context ->
            GlobalScope.launch(Dispatchers.IO) {
                val db = HomeDatabase.buildHomeDb(context)
                dao = db?.getDao()
                dao!!.insertNote(setUpFriendsArrayList())
            }
        }
    }

    private fun setupRV() {

        newsAdapter = HomeRVAdapter()
        newsAdapter.differ.submitList(setUpFriendsArrayList().toList())
        binding.expenseLayout.rvItemHome.apply {
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