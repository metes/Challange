package com.base.di

import com.base.commons.SharedPrefHelper
import com.base.network.APIClient
import com.base.ui.activity.MainActivityVM
import com.base.ui.fragment.songs.parent.SongsVM
import com.base.ui.fragment.splash.SplashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  Koin (D.I.) icin gerekli olan modullerin tanimlanmasi
 */
object Modules {

    val mainActivityViewModelModule = module {
        viewModel { MainActivityVM(get(), get()) }
        single { SharedPrefHelper(get()) }
        single { APIClient() }
    }
    val songsViewModelModule = module {
        viewModel { SongsVM(get(), get()) }
    }
    val splashViewModelModule = module {
        viewModel { SplashVM(get(), get()) }
    }


}
