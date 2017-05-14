package thackbarth.com.fragmentexample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity
{
    FragmentManager manager = null;

    TextView ConsoleOut;
    String consoleText ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ConsoleOut = (TextView) findViewById(R.id.ConsoleOut);

        manager = getFragmentManager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            TestMainFragements();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddAClick(View view)
    {
        Fragment1 Frag_A = new Fragment1();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        ft.add(R.id.container, Frag_A, "Frag_A");
        ft.commit();

        TestMainFragements();
    }

    private static final String TAG = "MainActivity";

    public void onRemAClick(View view)
    {

        Fragment1 Frag_A = (Fragment1) manager.findFragmentByTag("Frag_A");
        if (Frag_A != null)
        {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
            ft.remove(Frag_A);
            ft.commit();
        } else
        {
            Log.i(TAG, "onRemAClick: NOT FOUND");

            Toast.makeText(getApplicationContext(), "onRemAClick: Frag A NOT FOUND", Toast.LENGTH_SHORT).show();
        }

        TestMainFragements();

    }

    public void onRepAClick(View view)
    {
        Fragment2 Frag_B = new Fragment2();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        ft.replace(R.id.container, Frag_B, "Frag_B");
        ft.commit();

        TestMainFragements();
    }

    public void onRemBClick(View view)
    {
        Fragment2 Frag_A = (Fragment2) manager.findFragmentByTag("Frag_B");
        if (Frag_A != null)
        {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
            ft.remove(Frag_A);
            ft.commit();
        } else
        {
            Log.i(TAG, "onRemBClick: NOT FOUND");

            Toast.makeText(getApplicationContext(), "onRemBClick: Frag B NOT FOUND", Toast.LENGTH_SHORT).show();

        }

        TestMainFragements();
    }


    public void onResume()
    {
        LocalBroadcastManager.getInstance(this).registerReceiver(HandleLogMsg, new IntentFilter("Log"));
        super.onResume();
        TestMainFragements();
    }

    @Override
    public void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(HandleLogMsg);
        super.onPause();

        TestMainFragements();
    }

    private final BroadcastReceiver HandleLogMsg = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            String msg = intent.getStringExtra("msg");
            intent.putExtra("msg", TAG + " : onResume");
//            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            consoleText = consoleText + "\n\r"+msg;
            ConsoleOut.setText(consoleText);
         }
    };


    public void TestMainFragements()
    {

        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask()
        {
            public void run()
            {
                handler.post(new Runnable()
                {
                    public void run()
                    {



                        Fragment1 Frag_A = (Fragment1) manager.findFragmentByTag("Frag_A");
                        ChildFragment Child = (ChildFragment) manager.findFragmentByTag("child");

                        Fragment2 Frag_B = (Fragment2) manager.findFragmentByTag("Frag_B");

                        if (Frag_A != null)
                        {
                            Intent intent = new Intent("Log");
                            intent.putExtra("msg", "TestMainFragements: Frag_A Exists");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        }


                        if (Frag_B != null)
                        {
                            Intent intent = new Intent("Log");
                            intent.putExtra("msg", "TestMainFragements: Frag_B Exists");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        }


                        if (Child != null)
                        {
                            Intent intent = new Intent("Log");
                            intent.putExtra("msg", "TestMainFragements: CHILD Exists");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        }

                        if ( (Frag_A == null) && (Frag_B == null) && (Child == null) )
                        {
                            Intent intent = new Intent("Log");
                            intent.putExtra("msg", "TestMainFragements: All Fragments removed");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        }


                    }
                });
            }
        }, 1000);


    }
}
