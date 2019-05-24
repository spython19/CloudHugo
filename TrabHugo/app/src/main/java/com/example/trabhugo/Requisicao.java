package com.example.trabhugo;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requisicao extends AsyncTask<Void, Void, Void> {

    private Location location;
    private Context applicationContext;
    private StringBuilder response;

    public Requisicao(Location location, Context applicationContext) {
        this.location = location;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            HttpURLConnection urlConn;
            URL mUrl = new URL("https://214f30v7o4.execute-api.us-east-2.amazonaws.com/default/calculateDistanceToPuc");
            urlConn = (HttpURLConnection) mUrl.openConnection();
            urlConn.addRequestProperty("Content-Type", "application/json");
            urlConn.setRequestMethod("POST");
            String query = "{\"lat\": " + location.getLatitude() + ", \"lng\": " + location.getLongitude() + "}";
            urlConn.setRequestProperty("Content-Length", Integer.toString(query.length()));
            urlConn.getOutputStream().write(query.getBytes("UTF8"));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (!response.toString().equals("."))
            Toast.makeText(
                    applicationContext,
                    "Bem vindo à PUC " + response + " ",
                    Toast.LENGTH_LONG
            ).show();
    }

}