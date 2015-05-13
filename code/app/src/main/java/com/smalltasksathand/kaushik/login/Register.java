package com.smalltasksathand.kaushik.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends Activity {
    private static final String TAG ="" ;
    static EditText username;
    static EditText password;
    EditText cpassword;
    static EditText email;
    static EditText phone;
    TextView t_username;
    TextView t_password;
    TextView t_email;
    TextView t_phone;
    static TextView t_userexists;
    TextView t_wrongpass;
    static Connection con;
    static Context c;
    CheckBox emp;
    static boolean employer;
    static Activity activity;
    static ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username= (EditText)findViewById(R.id.editText3);
        password= (EditText) findViewById(R.id.editText4);
        cpassword=(EditText)findViewById(R.id.editText5);
        email=(EditText)findViewById(R.id.editText6);
        phone=(EditText) findViewById(R.id.editText7);
        t_username =(TextView)findViewById(R.id.textView4);
        t_password=(TextView)findViewById(R.id.editText6);
        t_email=(TextView)findViewById(R.id.textView10);
        t_phone=(TextView)findViewById(R.id.textView11);
        t_userexists=(TextView)findViewById(R.id.textView12);
        t_wrongpass=(TextView)findViewById(R.id.textView13);
        c=getApplication();
        emp=(CheckBox)findViewById(R.id.employer);
        activity=this;


        Button reg= (Button) findViewById(R.id.button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=ProgressDialog.show(Register.this,"Please wait","Verifying ...",true);
                boolean validation=validate();
                if(validation)
                {

                    employer=emp.isChecked();
                   // Toast.makeText(getApplicationContext(),"all fields are correct",Toast.LENGTH_SHORT).show();
                    String user=username.getText().toString();
                    String url="https://api.mongolab.com/api/1/databases/group3/collections/users?q={username:'"+user+"'}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                    con= new Connection();
                    try {
                        con.get(url);
                        Log.v(TAG,"in try");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"check details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   public static void executed(String result){
      if(result.length()>4) {
          t_userexists.setVisibility(View.VISIBLE);
          progress.dismiss();
          Toast.makeText(c,"username already exists",Toast.LENGTH_SHORT);
      }
       else
      {
         // Toast.makeText(,"check details",Toast.LENGTH_SHORT).show();
          Log.v(TAG,"In executed else");
          JSONObject user= new JSONObject();
          try {
              user.put("username",username.getText().toString());
              user.put("password",password.getText().toString());
              user.put("email", email.getText().toString());
              user.put("phone",phone.getText().toString());
              user.put("employer",String.valueOf(employer));
              String url="https://api.mongolab.com/api/1/databases/group3/collections/users?apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
              con.post(url,user);
          } catch (Exception e) {
              e.printStackTrace();
          }

      }





   }
    public static void oncreation(String result)
    {
        progress.dismiss();
        Toast.makeText(c,"Registration was successful",Toast.LENGTH_SHORT).show();
        activity.finish();
    }


    private boolean validate() {


        boolean val=true;
        int count=0;
       // username validation
        String user=username.getText().toString();
        if(user.contains("@")&&user.contains("_")&&user.contains("*"))
        {
            t_username.setVisibility(View.VISIBLE);
            return false;
        }
        else
            count++;


        //password validation
        String pass=password.getText().toString();
        if(pass.matches(".*[0-9].*")&&pass.matches(".*[a-z].*")&&pass.matches(".*[A-Z].*")&&pass!=null&&pass.length()>=8&&pass.length()<32)
            count++;
        else

            t_password.setVisibility(View.VISIBLE);


        //email validation
        String e=email.getText().toString();
        if((!e.matches(""))&&e.contains("@")&&e.contains("."))
        {
          if(e.indexOf('@')<e.indexOf('.'))
          {
              count++;
          }
          else
          {
              t_email.setVisibility(View.VISIBLE);

          }
        }
        else
        {
            t_email.setVisibility(View.VISIBLE);

        }

        //confirming the password
        String cpass=cpassword.getText().toString();
        if(cpass.equals(cpass))
            count++;
        else
            t_wrongpass.setVisibility(View.VISIBLE);

        //phone
        String no=phone.getText().toString();
        if(no.matches("[0-9]")&&(no.length()==10||no.length()==11))
            count++;
        else
        {
            phone.setVisibility(View.VISIBLE);

        }
        //checking all
        if(count==4)
            return true;
        else
            return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
