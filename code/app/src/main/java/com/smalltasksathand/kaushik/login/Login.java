package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;


public class Login extends Activity implements OnGetdata{

    private static final String TAG ="" ;
    static Context c;
   static String user;
    static ProgressDialog dialog;
    public final static String Extra_Message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c=getApplicationContext();
        setContentView(R.layout.activity_login);
        Button login=(Button)findViewById(R.id.login);
        final EditText username=(EditText)findViewById(R.id.editText);
        final EditText password=(EditText)findViewById(R.id.editText2);

        final TextView register= (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),Register.class);
                startActivity(in);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog= ProgressDialog.show(Login.this, "Please wait", "Verifying ...", true);
                String name=username.getText().toString();
                user=name;
                String pass=password.getText().toString();
                String url="https://api.mongolab.com/api/1/databases/group3/collections/users?q={username:'"+name+"',password:'"+pass+"'}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                Connection1 con= new Connection1();
                try {
                    con.get(url, Login.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void executed(String result)
    {
        Log.v(TAG,"in executed" );
        dialog.dismiss();
        boolean employer=false;
        if(result.length()>4)
        {
            try {
                JSONArray r= new JSONArray(result);
                String test=r.getJSONObject(0).getString("employer");
                if(test.equals("true"))
                    employer=true;
                else
                    employer=false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(employer) {
                Intent intent = new Intent(c, user_home.class);
                intent.putExtra(Extra_Message, user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(c, employee_home.class);
                intent.putExtra(Extra_Message, user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                c.startActivity(intent);

            }
        }
        else {
            Log.v(TAG,"in executed else");
            Toast.makeText(c, "wrong credentials", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void OnGetExecuted(String result) {
        
    }
}
