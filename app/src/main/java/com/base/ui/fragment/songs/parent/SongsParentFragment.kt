package com.base.ui.fragment.songs.parent

import com.base.R
import com.base.base.BaseFragment
import com.base.databinding.FragmentSongParentBinding
import com.base.ui.fragment.songs.child.SongTabFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SongsParentFragment : BaseFragment<FragmentSongParentBinding, SongsVM>() {

    override val layoutId: Int = R.layout.fragment_song_parent
    override val viewModel: SongsVM by sharedViewModel()

    override fun prepareViews() {
        changeToolbarVisibility(true)
        initTabs()
    }

    override fun initHandler() { }

    private fun initTabs() {
        ViewPagerAdapter(childFragmentManager).apply {
            addFragment(SongTabFragment(viewModel.favoritesSongsLD), getString(R.string.favoriler))
            addFragment(SongTabFragment(viewModel.allSongsLD), getString(R.string.kutuphane))
            binding.viewPagerBlogs.adapter = this
            binding.tabHostSongs.setupWithViewPager(binding.viewPagerBlogs)
        }
    }
}

