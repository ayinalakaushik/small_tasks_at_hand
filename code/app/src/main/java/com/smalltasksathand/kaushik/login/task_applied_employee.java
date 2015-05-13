package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class task_applied_employee extends ListActivity {
    JSONObject j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_task_applied_employee);
        Intent i=getIntent();
        try {
            JSONObject j=new JSONObject(i.getStringExtra("message"));
            String[] values = new String[] { j.getString("employee_id").toString(),"yashwanth" };
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
            Toast.makeText(getApplicationContext(),"click on employee to accept him",Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        applied_connection con= new applied_connection();
        // applied_connection1 con1=new applied_connection1();
        try {


            //Toast.makeText(getApplicationContext(),oid,Toast.LENGTH_SHORT).show();
          con.push("k","k","ayinalakausik","buy flowers","test");
            //  con1.push(descripton);
            Toast.makeText(getApplicationContext(),"task accepted",Toast.LENGTH_SHORT).show();
            finish();

            //deleting the accepted task
            /*applied_connection1 c=new applied_connection1();

            String k=(j.get("_id")).toString();
            JSONObject r=new JSONObject(k);
            String rr=r.get("$oid").toString();
            System.out.println(rr);
            c.push(rr);


            finish();*/
            // startActivity(getIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_applied_employee, menu);
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
