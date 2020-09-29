package com.makalaster.etherealdialpad.dsp;

/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
public interface ISynthService extends android.os.IInterface
{
  /** Default implementation for com.makalaster.etherealdialpad.dsp.ISynthService. */
  public static class Default implements ISynthService
  {
    // first finger on x-y pad

    @Override public void primaryOn() throws android.os.RemoteException
    {
    }
    @Override public void primaryOff() throws android.os.RemoteException
    {
    }
    @Override public void primaryXY(float x, float y) throws android.os.RemoteException
    {
    }
    // x and y must be between 0.0 and 1.0
    // second finger on x-y pad

    @Override public void secondaryOn() throws android.os.RemoteException
    {
    }
    @Override public void secondaryOff() throws android.os.RemoteException
    {
    }
    @Override public void secondaryXY(float x, float y) throws android.os.RemoteException
    {
    }
    // synth config

    @Override public float[] getScale() throws android.os.RemoteException
    {
      return null;
    }
    @Override public boolean isDuet() throws android.os.RemoteException
    {
      return false;
    }
    @Override public int getOctaves() throws android.os.RemoteException
    {
      return 0;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements ISynthService
  {
    private static final java.lang.String DESCRIPTOR = "com.makalaster.etherealdialpad.dsp.ISynthService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.makalaster.etherealdialpad.dsp.ISynthService interface,
     * generating a proxy if needed.
     */
    public static ISynthService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof ISynthService))) {
        return ((ISynthService)iin);
      }
      return new ISynthService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_primaryOn:
        {
          data.enforceInterface(descriptor);
          this.primaryOn();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_primaryOff:
        {
          data.enforceInterface(descriptor);
          this.primaryOff();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_primaryXY:
        {
          data.enforceInterface(descriptor);
          float _arg0;
          _arg0 = data.readFloat();
          float _arg1;
          _arg1 = data.readFloat();
          this.primaryXY(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_secondaryOn:
        {
          data.enforceInterface(descriptor);
          this.secondaryOn();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_secondaryOff:
        {
          data.enforceInterface(descriptor);
          this.secondaryOff();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_secondaryXY:
        {
          data.enforceInterface(descriptor);
          float _arg0;
          _arg0 = data.readFloat();
          float _arg1;
          _arg1 = data.readFloat();
          this.secondaryXY(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getScale:
        {
          data.enforceInterface(descriptor);
          float[] _result = this.getScale();
          reply.writeNoException();
          reply.writeFloatArray(_result);
          return true;
        }
        case TRANSACTION_isDuet:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isDuet();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getOctaves:
        {
          data.enforceInterface(descriptor);
          int _result = this.getOctaves();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements ISynthService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      // first finger on x-y pad

      @Override public void primaryOn() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_primaryOn, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().primaryOn();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void primaryOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_primaryOff, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().primaryOff();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void primaryXY(float x, float y) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(x);
          _data.writeFloat(y);
          boolean _status = mRemote.transact(Stub.TRANSACTION_primaryXY, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().primaryXY(x, y);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      // x and y must be between 0.0 and 1.0
      // second finger on x-y pad

      @Override public void secondaryOn() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_secondaryOn, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().secondaryOn();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void secondaryOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_secondaryOff, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().secondaryOff();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void secondaryXY(float x, float y) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(x);
          _data.writeFloat(y);
          boolean _status = mRemote.transact(Stub.TRANSACTION_secondaryXY, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().secondaryXY(x, y);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      // synth config

      @Override public float[] getScale() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        float[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getScale, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getScale();
          }
          _reply.readException();
          _result = _reply.createFloatArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public boolean isDuet() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isDuet, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isDuet();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int getOctaves() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOctaves, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOctaves();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static ISynthService sDefaultImpl;
    }
    static final int TRANSACTION_primaryOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_primaryOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_primaryXY = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_secondaryOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_secondaryOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_secondaryXY = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getScale = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_isDuet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_getOctaves = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    public static boolean setDefaultImpl(ISynthService impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static ISynthService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  // first finger on x-y pad

  public void primaryOn() throws android.os.RemoteException;
  public void primaryOff() throws android.os.RemoteException;
  public void primaryXY(float x, float y) throws android.os.RemoteException;
  // x and y must be between 0.0 and 1.0
  // second finger on x-y pad

  public void secondaryOn() throws android.os.RemoteException;
  public void secondaryOff() throws android.os.RemoteException;
  public void secondaryXY(float x, float y) throws android.os.RemoteException;
  // synth config

  public float[] getScale() throws android.os.RemoteException;
  public boolean isDuet() throws android.os.RemoteException;
  public int getOctaves() throws android.os.RemoteException;
}
