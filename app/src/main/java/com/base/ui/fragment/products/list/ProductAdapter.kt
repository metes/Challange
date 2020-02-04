package com.base.ui.fragment.products.list


import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.BR
import com.base.base.BaseAdapter
import com.base.base.BaseHolder
import com.base.databinding.ItemSongBinding

open class ProductAdapter<T>(
    flowItemList: ArrayList<T>,
    private val onFavoriteClick: (T?) -> Unit
) : BaseAdapter<T, ItemSongBinding, ProductHolder<T>>(flowItemList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder<T> {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding,  onFavoriteClick)
    }
}

class ProductHolder<T>(
    viewDataBinding: ItemSongBinding,
    private val onFavoriteClick: (T?) -> Unit
) : BaseHolder<T, ItemSongBinding>(viewDataBinding) {

    override fun bindingVariable(): Int {
        return BR.item
    }

    override fun bind() {
        getRowBinding()?.let {
            it.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }
}

