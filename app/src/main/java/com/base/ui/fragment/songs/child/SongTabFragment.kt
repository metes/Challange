package com.base.ui.fragment.songs.child

import com.base.R
import com.base.base.BaseFragment
import com.base.commons.SharedPrefHelper
import com.base.databinding.FragmentSplashBinding
import com.base.ui.fragment.songs.parent.SongsVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SongTabFragment : BaseFragment<FragmentSplashBinding, SongsVM>() {

    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SongsVM by viewModel()

    private val sharedPrefHelper: SharedPrefHelper by inject()

    override fun prepareViews() {
        changeToolbarVisibility()
    }

    override fun initHandler() { }



}

