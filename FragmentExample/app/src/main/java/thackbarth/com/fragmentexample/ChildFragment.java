package thackbarth.com.fragmentexample;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildFragment extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ChildFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildFragment newInstance(String param1, String param2)
    {
        ChildFragment fragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_child, container, false);

        final EditText et = (EditText) v.findViewById(R.id.sendMessage);
        Button b = (Button) v.findViewById(R.id.sendButton);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("Log");
                intent.putExtra("msg", "Msg send via child : "+et.getText().toString());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });

        return v;
    }

    public void onResume()
    {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(ChildMsgHandler, new IntentFilter("ChildMsg"));

        Intent intent = new Intent("Log");
        intent.putExtra("msg", TAG + " : onResume");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


        super.onResume();
    }

    private static final String TAG = "ChildFragment";

    @Override
    public void onPause()
    {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(ChildMsgHandler);
        Intent intent = new Intent("Log");
        intent.putExtra("msg", TAG + " : onPause");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        super.onPause();
    }

    private final BroadcastReceiver ChildMsgHandler = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            Log.i(TAG, "onReceive: GOT MESSAGE");

            String msg = intent.getStringExtra("msg");
            Intent intent1 = new Intent("Log");
            intent1.putExtra("msg", msg + " : Sent from Child");
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
        }
    };

    public void onSendMessageClick(View view)
    {
        Log.i(TAG, "onSendMessageClick: click");
    }
}
