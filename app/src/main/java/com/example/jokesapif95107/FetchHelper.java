package com.example.jokesapif95107;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FetchHelper extends AsyncTask<Void, Void, String> {
    private final String API_URL;
    @SuppressLint("StaticFieldLeak")
    private final TextView textView;

    public FetchHelper(String api_url, TextView textView) {
        API_URL = api_url;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... voids) {
        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(this.API_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "text/plain");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                String content = convertInputStream(is);
                is.close();
                System.out.println(content);
                return content;
            } else {
                return "Error! Cannot fetch from the API!";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertInputStream(InputStream is) {
        Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result != null) {
            textView.setText(result);
        }
    }


}
