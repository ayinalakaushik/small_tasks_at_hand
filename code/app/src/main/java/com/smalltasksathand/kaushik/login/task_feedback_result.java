package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class task_feedback_result extends Activity {

    static final String EXTRA_MESSAGE ="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_applied_result);


        Intent intent = getIntent();
        String message = intent.getStringExtra(task_feedback.EXTRA_MESSAGE);
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
        // String jobs[]=new String[j.size()];
        Set<String> s=new HashSet<>();
        for(int i=0;i<j.size();i++)
            try {
                s.add(j.get(i).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        String employee[]=s.toArray(new String[s.size()]);

        ArrayAdapter<String> ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,employee);
        l.setAdapter(ad);
        Toast.makeText(getApplicationContext(), "Employee you hired" +
                "", Toast.LENGTH_LONG).show();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(),"pressed "+id,Toast.LENGTH_SHORT).show();
                feedback_connection con= new feedback_connection();
                // connection1 con1=new connection1();
                try {
                    JSONObject temp=j.get((int) id);


                    Intent t=new Intent(getApplicationContext(),task_feedback_feedback.class);
                    t.putExtra(EXTRA_MESSAGE,temp.toString());
                    startActivity(t);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_feedback_result, menu);
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
