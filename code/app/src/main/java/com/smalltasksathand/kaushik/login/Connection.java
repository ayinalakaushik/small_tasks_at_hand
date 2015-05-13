package com.smalltasksathand.kaushik.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by kaushik on 4/5/2015.
 */
public class Connection {
    private static final String TAG ="" ;
    URL url;
    HttpPost request;
    JSONObject document;
    public void get(String temp) throws IOException {
        Log.v(TAG,"in get");
        getTask requestTask = new getTask();
        requestTask.execute();
        url=new URL(temp);


    }
    public void post(String url,JSONObject document)
    {
        request=new HttpPost(url);
        this.document=document;
        postTask p= new postTask();
        p.execute();
    }

    class postTask extends  AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String responseString = "";
            try {
                Log.v(TAG,"in posttask doin backgroung");
                HttpClient client = new DefaultHttpClient();
                request.setEntity(new ByteArrayEntity(document.toString().getBytes("UTF-8")));
                request.setHeader("Content-Type", "application/json");
                HttpResponse response = client.execute(request);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                    //..more logic
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;

        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            Log.v(TAG,"In posttask  onPostexecute ");
            Log.v(TAG,result);

            Register.oncreation(result);

        }
    }




     class getTask extends AsyncTask<String, String, String> {
        final String Tag = null;
        String responseString = "";
        BufferedReader in;


        public getTask(){

        }

        @Override
        protected String doInBackground(String... uri) {
            try {

                //  radius=radius*1000;
                Log.v(TAG,"in background");
                //String oid="5506312ce4b0603bec07c83c";
                //URL url= new URL("https://api.mongolab.com/api/1/databases/group3/collections/accept?q={}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5") ;
                BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
                responseString=br.readLine();

                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            Log.v(TAG,"in post execute");
            Log.v(TAG,result);
            Register.executed(result);


        }
    }
}