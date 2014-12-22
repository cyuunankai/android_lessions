package com.example.listviewwithcustomadapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment.SavedState;
import android.util.AttributeSet;
import android.view.View.BaseSavedState;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CheckableLinearLayout extends LinearLayout implements Checkable
{
private boolean             mChecked;
private static final String TAG               =CheckableLinearLayout.class.getCanonicalName();

public CheckableLinearLayout(final Context context)
  {
  super(context);
  setClickable(true);
  setLongClickable(true);
  }

public CheckableLinearLayout(final Context context,final AttributeSet attrs)
  {
  super(context,attrs);
  setClickable(true);
  setLongClickable(true);
  }

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public CheckableLinearLayout(final Context context,final AttributeSet attrs,final int defStyle)
  {
  super(context,attrs,defStyle);
  setClickable(true);
  setLongClickable(true);
  }

@Override
public void setChecked(final boolean checked)
  {
  mChecked=checked;
  }


@Override
public boolean isChecked()
  {
  return mChecked;
  }

@Override
public void toggle()
  {
  setChecked(!mChecked);
  }

@Override
public Parcelable onSaveInstanceState()
  {
  // Force our ancestor class to save its state
  final Parcelable superState=super.onSaveInstanceState();
  final SavedState savedState=new SavedState(superState);
  savedState.checked=isChecked();
  return savedState;
  }

@Override
public void onRestoreInstanceState(final Parcelable state)
  {
  final SavedState savedState=(SavedState)state;
  super.onRestoreInstanceState(savedState.getSuperState());
  setChecked(savedState.checked);
  requestLayout();
  }

// /////////////
// SavedState //
// /////////////
private static class SavedState extends BaseSavedState
  {
  boolean                                            checked;
  @SuppressWarnings("unused")
  public static final Parcelable.Creator<SavedState> CREATOR;
  static
    {
    CREATOR=new Parcelable.Creator<SavedState>()
      {
        @Override
        public SavedState createFromParcel(final Parcel in)
          {
          return new SavedState(in);
          }

        @Override
        public SavedState[] newArray(final int size)
          {
          return new SavedState[size];
          }
      };
    }

  SavedState(final Parcelable superState)
    {
    super(superState);
    }

  private SavedState(final Parcel in)
    {
    super(in);
    checked=(Boolean)in.readValue(null);
    }

  @Override
  public void writeToParcel(final Parcel out,final int flags)
    {
    super.writeToParcel(out,flags);
    out.writeValue(checked);
    }

  @Override
  public String toString()
    {
    return TAG+".SavedState{"+Integer.toHexString(System.identityHashCode(this))+" checked="+checked+"}";
    }
  }
}
