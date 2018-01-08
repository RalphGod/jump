package com.symbolplay.gamelibrary.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StreamUtils;
import com.symbolplay.gamelibrary.util.Logger;

// check http://stackoverflow.com/a/2793153/520229 for more info on GET/POST requests
public final class NetUtils {
    
    private static final String CHARSET = "UTF-8";
    
    private static final int GENERAL_TIMEOUT = 8000;
    private static final int CONNECT_TIMEOUT = GENERAL_TIMEOUT;
    private static final int READ_TIMEOUT = GENERAL_TIMEOUT;
    
    public static Object executeGetRequest(
            String urlString,
            Array<QueryParameter> queryParameters,
            boolean isByteArrayResult,
            int estimatedResultSize) {
        
        HttpURLConnection urlConnection = null;
        
        String queryString = "";
        try {
            queryString = getQueryString(queryParameters);
        } catch (UnsupportedEncodingException e) {
            Logger.error("Invalid encoding whild trying to construct query string.");
            return null;
        }
        
        String fullUrlString = StringUtils.isEmpty(queryString) ? urlString : urlString + "?" + queryString;
        
        try {
            URL url = new URL(fullUrlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setRequestProperty("Accept-Charset", CHARSET);
            
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            return isByteArrayResult ? StreamUtils.copyStreamToByteArray(inputStream, estimatedResultSize) : StreamUtils.copyStreamToString(inputStream, estimatedResultSize);
        } catch (MalformedURLException e) {
            Logger.error(e.getMessage());
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } catch (Exception e) {
            Logger.error(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        
        return null;
    }
    
    public static Object executePostRequest(
            String urlString,
            Array<QueryParameter> queryParameters,
            boolean isByteArrayResult,
            int estimatedResultSize) {
        
        HttpURLConnection urlConnection = null;
        
        String queryString = "";
        try {
            queryString = getQueryString(queryParameters);
        } catch (UnsupportedEncodingException e) {
            Logger.error("Invalid encoding whild trying to construct query string.");
            return null;
        }
        
        if (StringUtils.isEmpty(queryString)) {
            return null;
        }
        
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // Triggers POST.
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setRequestProperty("Accept-Charset", CHARSET);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
            
            OutputStream output = urlConnection.getOutputStream();
            output.write(queryString.getBytes(CHARSET));
            
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            return isByteArrayResult ? StreamUtils.copyStreamToByteArray(inputStream, estimatedResultSize) : StreamUtils.copyStreamToString(inputStream, estimatedResultSize);
        } catch (MalformedURLException e) {
            Logger.error(e.getMessage());
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } catch (Exception e) {
            Logger.error(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        
        return null;
    }
    
    private static String getQueryString(Array<QueryParameter> queryParameters) throws UnsupportedEncodingException {
        if (queryParameters == null || queryParameters.size == 0) {
            return "";
        }
        
        String queryString = "";
        queryString += queryParameters.get(0).getParameterString(CHARSET);
        for (int i = 1; i < queryParameters.size; i++) {
            queryString += "&" + queryParameters.get(i).getParameterString(CHARSET);
        }
        
        return queryString;
    }
}
