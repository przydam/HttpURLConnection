package com.google.damianprzybysz.httpurlconnection;

/**
 * Created by damian.przybysz on 13.02.2018.
 */


import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by damian.przybysz on 22.06.2017.
 */

public class HttpGetRequest extends AsyncTask<String, Void, String> {

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String username = "dug";
    public static final String password = "qqq123";
    public static final String usernameAndPassword = username + ":" + password;
    final String basicAuth = "Basic " + Base64.encodeToString((usernameAndPassword).getBytes(), Base64.NO_WRAP);

    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        //String data = params[1];


        OutputStream out = null;








        String result;
        String inputLine;

        try {


            String data = URLEncoder.encode("idagenta", "UTF-8")
                    + "=" + URLEncoder.encode(params[1], "UTF-8");

            data += "&" + URLEncoder.encode("dndstatus", "UTF-8") + "="
                    + URLEncoder.encode(params[2], "UTF-8");

            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);

            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);






            String usernameAndPassword = username + ":" + password;
            //String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );
            String authorizationString = "Basic " + Base64.encodeToString((usernameAndPassword).getBytes(), Base64.NO_WRAP); //Base64.NO_WRAP flag



            String baseAuthStr = username + ":" + password;
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");


            out = new BufferedOutputStream(connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));

            writer.write(data);

            writer.flush();

            writer.close();

            out.close();






            //Connect to our url
            connection.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();

            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }





}