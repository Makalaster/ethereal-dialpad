package com.makalaster.etherealdialpad.dsp

interface ISynthService {
    // first finger on x-y pad
    fun primaryOn()
    fun primaryOff()
    fun primaryXY(x: Float, y: Float)

    // second finger on x-y pad
    fun secondaryOn()
    fun secondaryOff()
    fun secondaryXY(x: Float, y: Float)

    // synth config
    fun getScale(): Array<Float>
    fun isDuet(): Boolean
    fun getOctaves(): Int
}