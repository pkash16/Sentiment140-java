package com.company;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.json.JSONArray;
import org.json.JSONObject;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static final String USER_AGENT = "Mozilla/5.0";
    public static JSONArray data;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //output
        System.out.println("Enter a query: ");

        String query_string = scanner.nextLine();


        // The factory instance is re-useable and thread safe.
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("blTWPrEOVm4wivuIMDuZDWJ8Z")
                .setOAuthConsumerSecret("KPhtWLKETn2VgYHUuVPS4VlsMFwLjViZv16UExxlA0JdTka8ep")
                .setOAuthAccessToken("1737850004-KawPRcYGZFVTw1hj5UPQt4TY6YXxUVTyEw3dTfs")
                .setOAuthAccessTokenSecret("QOStdXy5qDNNkNgs6p7RyAZSNJhZ4TWFsVgWHOOlRtqK2");


        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Query query = new Query(query_string).lang("en");
        query.setCount(100);

        data = new JSONArray();

        try {
            for(int i = 0; i < 5; i ++){
                QueryResult result = twitter.search(query);
                for (Status status : result.getTweets()) {

                    data.put(new JSONObject().put("text", removeUrl(status.getText())));
                }
                if(result.hasNext()){
                    query = result.nextQuery();
                }else{
                    break;
                }
            }

        }catch(Exception e){
            System.out.println(e.toString());
        }


        try{
           sendPost();
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }

    public static void sendPost() throws Exception {

        String url = "http://www.sentiment140.com/api/bulkClassifyJson?appid=pkash16@gmail.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        JSONObject json = new JSONObject();
        json.put("data", data);

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
        PrintWriter writer = new PrintWriter("json_interpreter/output.json");
        writer.println(response.toString());
        writer.close();
        System.out.println("Responses saved to output.json");
        System.out.println("attempting to open file.. if failed go to json_interpreter/index.html to view");
        //open website
        Desktop.getDesktop().browse(new File("json_interpreter/index.html").toURI());



    }



    private static String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }


}
