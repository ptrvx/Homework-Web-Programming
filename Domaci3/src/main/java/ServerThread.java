import com.google.gson.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Socket qotSocket;
    private BufferedReader qotIn;
    private PrintWriter qotOut;

    private Gson gson = new Gson();
    Random rand = new Random();

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

            qotSocket = new Socket("quotes.rest", 80);
            qotIn = new BufferedReader(new InputStreamReader(qotSocket.getInputStream()));
            qotOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(qotSocket.getOutputStream())));

            jsonQuotes.add(jsonLocal1);
            jsonQuotes.add(jsonLocal2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String request = in.readLine();
            System.out.println("Request: " + request);

            out.println(respond(quote()));

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String respond(String quoteOfTheDay) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n\r\n");
        response.append("<html><head><title>Quote of the Day</title></head>\n<body>");

        response.append(quoteOfTheDay);

        response.append("</body></html>");

        return response.toString();
    }

    private String quote() {
        qotOut.write("GET /qod HTTP/1.0\r\nHost: quotes.rest\r\nAccept: json\r\n\r\n");
        qotOut.flush();
        try {
            StringBuilder sb = new StringBuilder();
            boolean content = false;
            String line = qotIn.readLine();
            while(line != null) {
                if (line.equals(""))
                    content = true;
                if (content)
                    sb.append(line);
                line = qotIn.readLine();
            }

            String json = sb.toString();
            return parseJson(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String parseJson(String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        System.out.println(json);
        JsonObject contents;
        JsonArray quotes;
        try {
            contents = jsonObject.getAsJsonObject("contents");
            quotes = contents.getAsJsonArray("quotes");
        } catch (NullPointerException e) {
            System.out.println("Using local copy.");
            json = jsonQuotes.get(rand.nextInt(jsonQuotes.size()));   // Ako se prekoraci limit dozvoljen na quotes.rest/qot uzimam lokalnu kopiju json-a
            jsonObject = gson.fromJson(json, JsonObject.class);
            contents = jsonObject.getAsJsonObject("contents");
            quotes = contents.getAsJsonArray("quotes");
        }

        JsonElement q = quotes.get(0);
        JsonObject object = q.getAsJsonObject();
        String quote = object.get("quote").getAsString();
        String author = object.get("author").getAsString();

        return "<h1>" + quote + "</h1>" + "<h4>" + author + "</h4>";
    }

    private ArrayList<String> jsonQuotes = new ArrayList<String>();

    private final String jsonLocal1 = "{\"success\": {\"total\": 1},\"contents\": {\"quotes\": [{\"quote\": \"Do not worry if you have built your castles in the air. They are where they should be. Now put the foundations under them.\",\"length\": \"122\",\"author\": \"Henry David Thoreau\",\"tags\": [\"dreams\",\"inspire\",\"worry\"],\"category\": \"inspire\",\"date\": \"2016-11-21\",\"title\": \"Inspiring Quote of the day\",\"background\": \"https://theysaidso.com/img/bgs/man_on_the_mountain.jpg\",\"id\": \"mYpH8syTM8rf8KFORoAJmQeF\"}]}}";
    private final String jsonLocal2 = "{\n" +"  \"success\": {\n" +"    \"total\": 1\n" +"  },\n" +"  \"contents\": {\n" +"    \"quotes\": [\n" +"      {\n" +"        \"quote\": \"Persistence is very important. You should not give up unless you are forced to give up.\",\n" +"        \"length\": \"87\",\n" +"        \"author\": \"Elon Musk\",\n" +"        \"tags\": [\n" +"          \"inspire\",\n" +"          \"persistence\"\n" +"        ],\n" +"        \"category\": \"inspire\",\n" +"        \"date\": \"2019-03-20\",\n" +"        \"permalink\": \"https://theysaidso.com/quote/IzY6YlJCdhJc3IAlWvyHBAeF/elon-musk-persistence-is-very-important-you-should-not-give-up-unless-you-are-fo\",\n" +"        \"title\": \"Inspiring Quote of the day\",\n" +"        \"background\": \"https://theysaidso.com/img/bgs/man_on_the_mountain.jpg\",\n" +"        \"id\": \"IzY6YlJCdhJc3IAlWvyHBAeF\"\n" +"      }\n" +"    ],\n" +"    \"copyright\": \"2017-19 theysaidso.com\"\n" +"  }\n" +"}";
}

