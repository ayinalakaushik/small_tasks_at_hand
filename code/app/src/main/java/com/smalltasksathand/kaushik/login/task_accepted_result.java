package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class task_accepted_result extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_accepted_result);


        Intent intent = getIntent();
        String message = intent.getStringExtra(task_accepted.EXTRA_MESSAGE);
        ListView l= new ListView(this);
        setContentView(l);
        final ArrayList<JSONObject> j= new ArrayList<JSONObject>();
        int count=0;
        String temp="";
        for(int i=0;i<message.length();i++)
        {
            char c=message.charAt(i);

            if(c=='{')
                count++;
            if(count>0)
                temp=temp+c;
            if(c=='}')
            {
                count--;
                if(count==0)
                {
                    try {
                        j.add(new JSONObject(temp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(temp);
                    temp="";
                }


            }
        }

        //converting arraylist to String array of jobs
        String jobs[]=new String[j.size()];
        for(int i=0;i<j.size();i++)
            try {
                jobs[i]=j.get(i).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        ArrayAdapter<String> ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jobs);
        l.setAdapter(ad);
        String TAG="";
        Log.v(TAG, "trail");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_accepted, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
