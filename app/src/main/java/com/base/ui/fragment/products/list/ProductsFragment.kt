package com.base.ui.fragment.products.list

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.R
import com.base.base.BaseFragment
import com.base.databinding.FragmentSongChildBinding
import com.base.model.retrofit.response.songListResponse.SongListResponse
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : BaseFragment<FragmentSongChildBinding, ProductsVM>() {

    override val layoutId: Int = R.layout.fragment_song_child
    override val viewModel: ProductsVM by viewModel()

    private var productAdapter: ProductAdapter<SongListResponse>? = null

    override fun prepareViews() {
        prepareRecyclerView()
    }

    override fun subscribe() {
        viewModel.allProductsLD.observeThis {
            binding.swipeRefresh.isRefreshing = false
            productAdapter?.changeDataWithThis(it)
            productAdapter?.notifyDataSetChanged()
        }
    }

    private fun prepareRecyclerView() {
        productAdapter = ProductAdapter(::onFavoritesClicked)
        binding.recyclerViewSongs.apply {
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = productAdapter
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchSongs()
        }
    }


    private fun onFavoritesClicked(item: SongListResponse?) {
        item?.let {
//            viewModel.updateFavorites(it)
        }
    }




}

