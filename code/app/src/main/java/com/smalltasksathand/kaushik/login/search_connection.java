package com.smalltasksathand.kaushik.login;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by kaushik on 5/3/2015.
 */
public class search_connection {
    private static final String TAG ="connection" ;

    public void push(String id,String tid,String e_id,String description,String title) throws IOException {
        RequestTask requestTask = new RequestTask(id,tid,e_id,description,title);
        requestTask.execute("");
    }


    class RequestTask extends AsyncTask<String, String, String> {
        String id,tid,e_id,description,title;


        public RequestTask(String id,String tid,String e_id,String description,String title){
            this.id=id;
            this.tid=tid;
            this.e_id=e_id;
            this.description=description;
            this.title=title;
        }

        @Override
        protected String doInBackground(String... uri) {
            String responseString = "";
            try {
                Log.i(TAG, "start of try");
                HttpClient client = new DefaultHttpClient();
                String url = "https://api.mongolab.com/api/1/databases/group3/collections/applied?apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                HttpPost request = new HttpPost(url);
                //creating json objects
                // double latLong[] = {longitude,latitude};
                //JSONArray jsonArray = new JSONArray(latLong);
                //JSONObject jobj = new JSONObject().put("type", "point");
                //jobj.put("coordinates", jsonArray);
                //  JSONObject location= new JSONObject();
                // location.put("type","Point");
                // location.put("coordinates",jsonArray);
                JSONObject document = new JSONObject();
                document.put("employee_id",id);
                document.put("employer_id",e_id);
                document.put("title",title);
                document.put("tid",Long.valueOf(tid));
                document.put("description",description);
                document.put("a_id",System.currentTimeMillis());
                // document.put( "loc",location);

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
                Log.i(TAG, "executed the code");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }
}

