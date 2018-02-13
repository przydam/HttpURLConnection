package com.google.damianprzybysz.httpurlconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView content;
    EditText fname, email, login, pass;
    String Name, Email, Login, Pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CallAPI getRequest = new  CallAPI();
       // Object myUrl;
       // String result = getRequest.execute(myUrl).get();


        content    =   (TextView)findViewById( R.id.content);
        fname      =   (EditText)findViewById(R.id.name);
        email      =   (EditText)findViewById(R.id.email);
        login      =    (EditText)findViewById(R.id.loginname);
        pass       =   (EditText)findViewById(R.id.password);

        final String dndstatus = "1";
        final String idagenta = "923";


        Button saveme=(Button)findViewById(R.id.save);

        saveme.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                try{

                    // CALL GetText method to make post method call
                    //GetText();



                    String result = null;

                    //Instantiate new instance of our class
                    HttpGetRequest getRequest = new HttpGetRequest();

                   // String myUrl = "http://85.14.79.181/damian/getQueuesForCallcenter.php";
                    String myUrl = "http://85.14.79.181/damian/OffDND.php";

                    try {
                        result = getRequest.execute(myUrl,idagenta, dndstatus).get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    content.setText(result);















                }
                catch(Exception ex)
                {
                    content.setText(" url exeption! " );
                }
            }
        });



















    }



    public  void  GetText()  throws UnsupportedEncodingException
    {

        final String REQUEST_METHOD = "POST";
        final int READ_TIMEOUT = 15000;
        final int CONNECTION_TIMEOUT = 15000;
        final String username = "dug";
        final String password = "qqq123";
        String usernameAndPassword;
        final String basicAuth = "Basic " + Base64.encodeToString("dug:qqq123".getBytes(), Base64.NO_WRAP);


        // Get user defined values
        Name = fname.getText().toString();
        Email   = email.getText().toString();
        Login   = login.getText().toString();
        Pass   = pass.getText().toString();

        // Create data variable for sent values to server

        String data = URLEncoder.encode("name", "UTF-8")
                + "=" + URLEncoder.encode(Name, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(Login, "UTF-8");

        data += "&" + URLEncoder.encode("pass", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
           // URL url = new URL("http://85.14.79.181/damian/login.php");

            URL url = new URL("http://85.14.79.181/damian/getNOCQueuesForCallcenter.php");
            HttpURLConnection conn =(HttpURLConnection)
                    url.openConnection();
            // Send POST data request

           // URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            //Set methods and timeouts
            conn.setRequestMethod(REQUEST_METHOD);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);

            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");



            usernameAndPassword = username + ":" + password;
            //String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );
            String authorizationString = "Basic " + Base64.encodeToString((usernameAndPassword).getBytes(), Base64.NO_WRAP); //Base64.NO_WRAP flag



            String baseAuthStr = username + ":" + password;
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            conn.connect();


            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        content.setText(text);

    }




}
