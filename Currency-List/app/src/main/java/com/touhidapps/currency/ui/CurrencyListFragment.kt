package com.touhidapps.currency.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.touhidapps.currency.App
import com.touhidapps.currency.adapter.CurrencyAdapter
import com.touhidapps.currency.databinding.FragmentCurrencyListBinding
import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.utils.Const
import com.touhidapps.currency.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyListFragment: Fragment() {

    lateinit var binding: FragmentCurrencyListBinding
    lateinit var currencyAdapter: CurrencyAdapter
    private val currencyList: ArrayList<CurrencyEntity> = arrayListOf()
    private val currencyViewModel: CurrencyViewModel by viewModels()

    private val itemDecorator by lazy {
        MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
            dividerInsetEnd = 30
            dividerInsetStart = 30
        }
    }

    private var myListType: ListType = ListType.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myListType = arguments?.getSerializable(Const.KEY_CURRENCY_TYPE) as ListType

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        listeners()
        observers()

    } // onViewCreated

    override fun onResume() {
        super.onResume()
        binding.swipeRefresh.post {
            getCurrencies(myListType)
        }
    }


    private fun initUI() {

        currencyAdapter = CurrencyAdapter(currencyList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = currencyAdapter

    } // initUI

    private fun observers() {

        lifecycleScope.launch {
            currencyViewModel.currencyListLive.observe(this@CurrencyListFragment) { allData ->
                currencyList.clear()
                currencyList.addAll(allData)

                currencyAdapter.notifyDataSetChanged()

                if (allData.isEmpty()) {
                    binding.llNoDataMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = "No Results"
                } else {
                    binding.llNoDataMessage.visibility = View.GONE
                }

                binding.swipeRefresh.isRefreshing = false

            }
        }

    } // observers

    private fun getCurrencies(listType: ListType, searchText: String? = "") {

        binding.swipeRefresh.isRefreshing = true
        currencyViewModel.getCurrencies(listType, searchText)

    } // getCurrencies

    private fun listeners() {

        currencyAdapter.setItemClick {
            // TODO action after click
        }

        binding.swipeRefresh.setOnRefreshListener {
            getCurrencies(myListType)
        }

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.recyclerView.addItemDecoration(itemDecorator)
            } else {
                binding.recyclerView.removeItemDecoration(itemDecorator)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                getCurrencies(myListType, newText)

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

    } // listeners


}