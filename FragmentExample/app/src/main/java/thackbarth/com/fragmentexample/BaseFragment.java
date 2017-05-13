package thackbarth.com.fragmentexample;
/*
 * Created by tomhackbarth on 5/12/17.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class BaseFragment extends Fragment
{
    private static final String TAG = "BaseFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate: "+this.getClass().getName());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        Log.i(TAG, "onAttach: "+this.getClass().getName());
        super.onAttach(context);
    }

    @Override
    public void onDestroy()
    {
        Log.i(TAG, "onDestroy: "+this.getClass().getName());
        super.onDestroy();
    }



    @Override
    public void onDetach()
    {
        Log.i(TAG, "onDetach: "+this.getClass().getName());
        super.onDetach();
    }

    @Override
    public void onPause()
    {
        Log.i(TAG, "onPause: "+this.getClass().getName());
        super.onPause();
    }

    @Override
    public void onResume()
    {
        Log.i(TAG, "onResume: "+this.getClass().getName());
        super.onResume();
    }
}
