package com.makalaster.ethereal_dialpad.pads.grid

import android.os.RemoteException

interface ISynthService {
    // first finger on x-y pad
    @Throws(RemoteException::class)
    fun primaryOn()

    @Throws(RemoteException::class)
    fun primaryOff()

    @Throws(RemoteException::class)
    fun primaryXY(x: Float, y: Float)

    @get:Throws(RemoteException::class)
    val scale: FloatArray

    @get:Throws(RemoteException::class)
    val isDuet: Boolean

    @get:Throws(RemoteException::class)
    val octaves: Int
}
