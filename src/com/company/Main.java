package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import twitter4j.*;

public class Main {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //output
        System.out.println("Enter a query: ");

        String query_string = scanner.nextLine();


        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(query_string);
        try {
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                System.out.println(status.getText());
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }

	    Main http = new Main();
        try{
            http.sendPost();
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }

    private void sendGet() throws Exception {

        String url = "http://www.sentiment140.com/api/classify?text=new+moon+is+awesome&query=new+moon";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    private void sendPost() throws Exception {

        String url = "http://www.sentiment140.com/api/bulkClassifyJson";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        array.put(new JSONObject().put("text", "I love titanic."));
        array.put(new JSONObject().put("text", "I love that i hate this"));
        json.put("data",array);

        String urlParameters = json.toString();


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }


}
