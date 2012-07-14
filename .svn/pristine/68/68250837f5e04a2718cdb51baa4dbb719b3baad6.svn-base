package com.sap.appsexperience.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class AppUtil {
	public static final String TAG = "OndeAbastecer_Util";

	public static String httpGet(String url) {
		StringBuffer responseString = new StringBuffer();
		
		HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        try
        {
            HttpResponse response = client.execute(request);
            
            //BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            
            line = EntityUtils.toString(response.getEntity(), "UTF-8");
            responseString.append(line);
            //while((line = reader.readLine()) != null) {
            //	responseString.append(line);
            //}
	
	    }
	    catch(Exception e)
	    {
	    	if(e != null)
	    		e.printStackTrace();
	    }
	    
		return responseString.toString();
	}

}
