package com.makalaster.ethereal_dialpad.pads.grid

import android.os.RemoteException

interface ISynthService {
    @get:Throws(RemoteException::class)
    val scale: FloatArray

    @get:Throws(RemoteException::class)
    val isDuet: Boolean

    @get:Throws(RemoteException::class)
    val octaves: Int
}
