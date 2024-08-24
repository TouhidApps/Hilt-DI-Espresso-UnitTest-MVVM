package com.touhidapps.currency

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.touhidapps.currency.databinding.ActivityDemoBinding
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.ui.CurrencyListFragment
import com.touhidapps.currency.utils.Const
import com.touhidapps.currency.utils.addFragment
import com.touhidapps.currency.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
public class DemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityDemoBinding

    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        listeners()
        observers()

    } // onCreate

    private fun listeners() {

        binding.btnClearAll.setOnClickListener {
            clearAllCurrenciesAlert()
        }

        binding.btnInsert.setOnClickListener {
            currencyViewModel.addAllCurrencies()
        }

        binding.btnCrypto.setOnClickListener {

            val currencyFragment = CurrencyListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Const.KEY_CURRENCY_TYPE, ListType.CRYPTO)
                }
            }
            addFragment(this@DemoActivity, currencyFragment)

        }

        binding.btnFiat.setOnClickListener {
            val currencyFragment = CurrencyListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Const.KEY_CURRENCY_TYPE, ListType.FIAT)
                }
            }
            addFragment(this@DemoActivity, currencyFragment)
        }

        binding.btnShowAll.setOnClickListener {
            val currencyFragment = CurrencyListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Const.KEY_CURRENCY_TYPE, ListType.ALL)
                }
            }
            addFragment(this@DemoActivity, currencyFragment)
        }

    } // listeners

    private fun observers() {

        lifecycleScope.launch {
            currencyViewModel.msgDataLive.observe(this@DemoActivity) { value ->
                Toast.makeText(this@DemoActivity, value, Toast.LENGTH_SHORT).show()
            }
        }

    } // observers

    private fun clearAllCurrenciesAlert() {

        AlertDialog.Builder(this).apply {
            setTitle("Alert!")
            setMessage("Do you want to clear all currency data?")
            setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    currencyViewModel.deleteAllCurrencies()
                })
            setNegativeButton(
                android.R.string.no,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
        }.show()

    } // clearAllCurrencies



}