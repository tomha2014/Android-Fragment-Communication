package thackbarth.com.fragmentexample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity
{
    FragmentManager manager = null;

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

        manager = getFragmentManager();

//        Fragment1 f1 = new Fragment1();
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(R.id.container, f1, "f1");
//        ft.commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddAClick(View view)
    {
        Fragment1 f1 = new Fragment1();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        ft.add(R.id.container, f1, "f1");
        ft.commit();
    }

    private static final String TAG = "MainActivity";

    public void onRemAClick(View view)
    {

        Fragment1 f1 = (Fragment1) manager.findFragmentByTag("f1");
        if (f1 != null)
        {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
            ft.remove(f1);
            ft.commit();
        } else
        {
            Log.i(TAG, "onRemAClick: NOT FOUND");
        }
    }

    public void onRepAClick(View view)
    {
        Fragment2 f2 = new Fragment2();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        ft.replace(R.id.container, f2, "f2");
        ft.commit();
    }

    public void onRemBClick(View view)
    {
        Fragment2 f1 = (Fragment2) manager.findFragmentByTag("f2");
        if (f1 != null)
        {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
            ft.remove(f1);
            ft.commit();
        } else
        {
            Log.i(TAG, "onRemBClick: NOT FOUND");
        }
    }

    public void onSendClick(View view)
    {
        Intent intent = new Intent("TestMsg");

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
