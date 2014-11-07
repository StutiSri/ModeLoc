package com.example.automode;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HitUrl 
{
    public String readJSONFeed(String URL)
    {
        StringBuilder string = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        try 
        {
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null)
                {
                	string.append(line);
                }
                is.close();
            } 
            else
            {
                Log.d("JSON", "Failed to download file");
            }
        } 
        catch (Exception e)
        {
        	
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }        
        return string.toString();
    }
}

