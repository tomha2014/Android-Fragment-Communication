package thackbarth.com.fragmentexample;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment1()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance()
    {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);

        ChildFragment f1 = new ChildFragment();
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.add(R.id.child_container, f1, "child");
        ft.commit();

        // Inflate the layout for this fragment
        return v;
    }


    private static final String TAG = "Fragment1";

    @Override
    public void onResume()
    {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(ChangeDepartment, new IntentFilter("TestMsg"));


        super.onResume();
    }

    @Override
    public void onPause()
    {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(ChangeDepartment);

        Log.i(TAG, "onResume: ====================");

        // To demo the issue change the tag from "child" to "kid"
        // something it can not find
        // click on send msg after removing fragement A

        ChildFragment f1 = (ChildFragment) getActivity().getFragmentManager().findFragmentByTag("child");
        if (f1 != null)
        {
            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
            ft.remove(f1);
            ft.commit();
        } else
        {
            Log.i(TAG, "ChildFragment: NOT FOUND");
        }

        Log.i(TAG, "onResume: ====================");

        super.onPause();
    }

    private final BroadcastReceiver ChangeDepartment = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            Log.i(TAG, "onReceive: GOT MESSAGE");
        }
    };
}
