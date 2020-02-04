package com.base.ui.fragment.products.list

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.R
import com.base.base.BaseFragment
import com.base.commons.toArrayList
import com.base.databinding.FragmentSongChildBinding
import com.base.model.retrofit.response.songListResponse.SongListResponse
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : BaseFragment<FragmentSongChildBinding, ProductsVM>() {

    override val layoutId: Int = R.layout.fragment_song_child
    override val viewModel: ProductsVM by viewModel()

    private var songAdapter: ProductAdapter<SongListResponse>? = null

    override fun prepareViews() {
        prepareRecyclerView()
    }

    override fun subscribe() {
        viewModel.allSongsLD.observeThis {
            songAdapter?.notifyDataSetChanged()
        }
    }

    private fun prepareRecyclerView() {
        songAdapter = ProductAdapter(viewModel.allSongsLD.value!!.toArrayList(), ::onFavoritesClicked)
        binding.recyclerViewSongs.apply {
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = songAdapter
        }
    }


    private fun onFavoritesClicked(item: SongListResponse?) {
        item?.let {
//            viewModel.updateFavorites(it)
        }
    }




}

