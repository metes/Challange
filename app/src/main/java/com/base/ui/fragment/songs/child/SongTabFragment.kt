package com.base.ui.fragment.songs.child

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.R
import com.base.base.BaseFragment
import com.base.databinding.FragmentSongChildBinding
import com.base.model.local.SongListWraper
import com.base.ui.fragment.songs.parent.SongsVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SongTabFragment(
    private val itemList: LiveData<ArrayList<SongListWraper>>) : BaseFragment<FragmentSongChildBinding, SongsVM>() {

    override val layoutId: Int = R.layout.fragment_song_child
    override val viewModel: SongsVM by sharedViewModel()

    private var songAdapter: SongAdapter<SongListWraper>? = null

    override fun prepareViews() { }

    override fun subscribe() {
        itemList.observe(viewLifecycleOwner, Observer {
            prepareRecyclerView(it)
        })
    }

    private fun prepareRecyclerView(list: ArrayList<SongListWraper>) {
        songAdapter = SongAdapter(itemList.value!!, ::onPlayClicked, ::onFavoritesClicked)
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

    private fun onPlayClicked(item: SongListWraper?, button: View?) {
        item?.let {
            if (item.isPlaying == true) {
                viewModel.stopAudio(item)
            } else {
                viewModel.playAudio(item, button) { changePlayPauseIcon(item, it) }
            }
            changePlayPauseIcon(item, button)
        }
    }

    private fun onFavoritesClicked(item: SongListWraper?) {
        item?.let {
            viewModel.updateFavorites(it)
            songAdapter?.notifyDataSetChanged()
        }
    }

    private fun changePlayPauseIcon(item: SongListWraper, button: View?) {
        item.isPlaying = item.isPlaying != true
        val iconResId =
            if (item.isPlaying == true) R.drawable.ic_pause_black_24dp else R.drawable.ic_play_arrow_black_24dp

        (button as AppCompatImageButton).setImageResource(iconResId)
    }





}

