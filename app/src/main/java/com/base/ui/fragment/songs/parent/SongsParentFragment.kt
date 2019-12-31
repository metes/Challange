package com.base.ui.fragment.songs.parent

import com.base.R
import com.base.base.BaseFragment
import com.base.commons.SharedPrefHelper
import com.base.databinding.FragmentSongParentBinding
import com.base.ui.fragment.songs.child.SongTabFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongsParentFragment : BaseFragment<FragmentSongParentBinding, SongsVM>() {

    override val layoutId: Int = R.layout.fragment_song_parent
    override val viewModel: SongsVM by viewModel()

    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun prepareViews() {
        initTabs()
    }

    override fun initHandler() { }



    private fun initTabs() {
        ViewPagerAdapter(childFragmentManager).apply {
            if (count == 0) {
                addFragment(SongTabFragment(), getString(R.string.favoriler))
                addFragment(SongTabFragment(), getString(R.string.kutuphane))
                binding.viewPagerBlogs.adapter = this
                binding.tabHostSongs.setupWithViewPager(binding.viewPagerBlogs)
            }
        }
    }
}

