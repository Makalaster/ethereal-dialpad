package com.makalaster.etherealdialpad.dsp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object SynthModule {
    @ViewModelScoped
    @Provides
    fun bindSynth(): Synth = Synth()
}