package com.base.ui.fragment.songs.child

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BR
import com.base.base.BaseAdapter
import com.base.base.BaseHolder
import com.base.databinding.ItemSongBinding

open class SongAdapter<T>(
    flowItemList: ArrayList<T>,
    private val onPlayClick: (T?, View?) -> Unit,
    private val onFavoriteClick: (T?) -> Unit
) : BaseAdapter<T, ItemSongBinding, SongHolder<T>>(flowItemList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder<T> {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return SongHolder(binding, onPlayClick, onFavoriteClick)
    }
}


class SongHolder<T>(
    viewDataBinding: ItemSongBinding,
    val onPlayClick: (T?, View?) -> Unit,
    val onFavoriteClick: (T?) -> Unit
) : BaseHolder<T, ItemSongBinding>(viewDataBinding) {

    override fun bindingVariable(): Int {
        return BR.item
    }

    override fun bind() {
        getRowBinding()?.let {
            it.btnPlay.setOnClickListener { onPlayClick(item, it) }
            it.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }
}

