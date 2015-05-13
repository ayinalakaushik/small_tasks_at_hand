package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


public class task_search extends Activity {

    static double longitude;
    static double latitude;
    static TextView t;
    String user;
    int radius;
    public final static String EXTRA_MESSAGE = "";
    class Connection {
        public void get(int radius, double longitude,double latitude) throws IOException {
            RequestTask requestTask = new RequestTask(radius,longitude,latitude);
            requestTask.execute();
        }


        class RequestTask extends AsyncTask<String, String, String> {
            int radius;
            double longitude,latitude;
            final String Tag = null;
            String responseString = "";
            BufferedReader in;


            public RequestTask(int radius, double longitude,double latitude){
                this.radius=radius;
                this.longitude=longitude;
                this.latitude=latitude;
            }

            @Override
            protected String doInBackground(String... uri) {
                try {
                   /* HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    String url ="https://api.mongolab.com/api/1/databases/group3/collections/Jobs?q={loc:{$geoWithin:{$centerSphere:[[-94.5844458,39.0418465],1000]}}}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                   System.out.print(url);
                    URL u=new URL(url);
                    request.setURI(new URI(url));
                    HttpResponse response = client.execute(request);
                    response.getStatusLine().getStatusCode();

                    in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String l = "";
                    String nl = System.getProperty("line.separator");
                    while ((l = in.readLine()) !=null){
                        sb.append(l + nl);
                    }
                    in.close();
                    responseString = sb.toString();*/
                    radius=radius*(1609);
                    URL url= new URL("https://api.mongolab.com/api/1/databases/group3/collections/Jobs?q={loc:{$geoWithin:{$centerSphere:[["+longitude+","+latitude+"],"+radius+"]}}}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5") ;
                    BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
                    responseString=br.readLine();

                    return responseString;
                } catch (Exception e) {
                    e.printStackTrace();
                }  /*finally{
                    if (in != null){
                        try{
                            in.close();
                            return responseString;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }*/

                return responseString;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Do anything with response..
                Intent intent = new Intent(getApplicationContext(), task_search_result.class);
                Bundle b=new Bundle();
                b.putString("result",result);
                b.putString("user",user);
                intent.putExtras(b);
                startActivity(intent);

                //execute();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_search);
        Intent i=getIntent();
        user=i.getStringExtra(employee_home.Extra_Message);

        Button search = (Button)findViewById(R.id.search_button);
        final EditText r=(EditText)findViewById(R.id.search_editText);
        // final TextView result = (TextView) findViewById(R.id.result);
        t=(TextView)findViewById(R.id.search_textView15);
        final RadioButton map=(RadioButton)(findViewById(R.id.search_radioButton2));
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
                Intent in=new Intent(getApplicationContext(),search_MAPS.class);
               // Log.v(TAG, "after intent");
                startActivityForResult(in, 1);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!map.isChecked()){
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                List<String> matchingProviders = locationManager.getAllProviders();
                Location bestResult = null;
                float bestAccuracy = 1000;
                for (String provider : matchingProviders) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        float accuracy = location.getAccuracy();
                        long time = location.getTime();
                        if ((accuracy < bestAccuracy)) {
                            bestResult = location;
                        } else if (bestAccuracy == Float.MAX_VALUE) {
                            bestResult = location;
                        }
                    }
                }
                longitude = bestResult.getLongitude();
                latitude = bestResult.getLatitude();}
                radius = Integer.parseInt(r.getText().toString());
                Connection con = new Connection();
                //  Toast.makeText(getApplicationContext(),r.getText().toString()+"-"+longitude+latitude,Toast.LENGTH_SHORT).show();
                try {
                    con.get(radius, longitude, latitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public static void update(LatLng l)
    {
        latitude=l.latitude;
        longitude=l.longitude;
        t.setText(l.toString());
        t.setVisibility(View.VISIBLE);

    }//on
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_search, menu);
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
