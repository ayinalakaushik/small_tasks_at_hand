package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public class job_post extends Activity {
    private static final String TAG ="" ;
    static double longitude;
    static double latitude;
    static TextView t;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
        Intent intent=getIntent();
       user=intent.getStringExtra(Login.Extra_Message);
        longitude=0;latitude=0;
        t=(TextView)findViewById(R.id.post_textView19);
        final EditText description = (EditText) findViewById(R.id.post_description);
        final EditText title = (EditText) findViewById(R.id.post_title);
        final EditText time = (EditText) findViewById(R.id.post_editText8);
        final EditText price = (EditText) findViewById(R.id.post_editText9);
        Button post= (Button) findViewById(R.id.post_button2);
        final RadioButton map=(RadioButton)(findViewById(R.id.post_radioButton2));
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),MAPS.class);
                Log.v(TAG,"after intent");
                startActivityForResult(in, 1);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),description.getText(),Toast.LENGTH_SHORT).show();
                //System.out.print("sucess");
                try {
                    post_connection con= new post_connection();
                    if(!map.isChecked())
                    {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        List<String> matchingProviders = locationManager.getAllProviders();
                        Location bestResult = null;
                        float bestAccuracy=1000;
                        for (String provider: matchingProviders) {
                            Location location = locationManager.getLastKnownLocation(provider);
                            if (location != null) {
                                float accuracy = location.getAccuracy();
                                long time = location.getTime();
                                if ((accuracy < bestAccuracy)) {
                                    bestResult = location;
                                }
                                else if ( bestAccuracy == Float.MAX_VALUE ){
                                    bestResult = location;
                                }
                            }
                        }
                        longitude=bestResult.getLongitude();
                        latitude=bestResult.getLatitude();
                    }
                    con.push(user,title.getText().toString(),description.getText().toString(),longitude,latitude,time.getText().toString(),price.getText().toString());
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"task posted",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static void update(LatLng l)
    {
        latitude=l.latitude;
        longitude=l.longitude;
        t.setText(l.toString());
        t.setVisibility(View.VISIBLE);

    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_post, menu);


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
