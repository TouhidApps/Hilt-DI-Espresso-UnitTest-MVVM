package com.touhidapps.currency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.touhidapps.currency.databinding.RowCurrencyBinding
import com.touhidapps.currency.db.entity.CurrencyEntity

class CurrencyAdapter(var items: ArrayList<CurrencyEntity>): RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>() {

    lateinit var onItemClick: ((CurrencyEntity) -> Unit)

    fun setItemClick(action: (CurrencyEntity) -> Unit) {
        onItemClick = action
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowCurrencyBinding = RowCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding

        binding.tvCurrencyNameImage.text = (item.name ?: ".").first().toString()
        binding.tvCurrencyName.text = item.name ?: ""
        binding.tvCurrencyCode.text = item.code ?: ""

    }

    inner class MyViewHolder(var binding: RowCurrencyBinding): ViewHolder(binding.root) {
        init {
            // Click listeners
            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }
    }

}