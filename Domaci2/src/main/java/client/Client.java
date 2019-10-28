package client;

import com.google.gson.Gson;
import model.Intent;
import model.Message;
import model.Response;
import model.Status;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class Client implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Gson gson;

    private String id;
    private Boolean seated;
    private Random random;


    public Client() throws IOException {
        this.socket = new Socket("localhost", 9393);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gson = new Gson();
        this.random = new Random();

        this.id = UUID.randomUUID().toString();
        this.seated = false;
    }

    public void run() {
        try {
            if(!this.seated) {
                Message message = new Message();
                message.setId(this.id);
                message.setIntent(Intent.REQUEST_CHAIR);
                String convertedMessage = gson.toJson(message);

                write(convertedMessage);

                String responseStr = in.readLine();
                Response response = gson.fromJson(responseStr, Response.class);

                if (response.getStatus().equals(Status.OK)) {
                    this.seated = true;
                    System.out.println("Klijent " + id + " je seo za sto.");
                } else {
                    System.out.println("Klijent " + id + " nije uspeo da sedne za sto.");
                }



                while(seated) {
                    responseStr = in.readLine();
                    response = gson.fromJson(responseStr, Response.class);

                    if (response.getIntent().equals(Intent.DRAW)) {
                        message.setIntent(Intent.DRAW);
                        int limit = ((Double)response.getData()).intValue();
                        message.setData(random.nextInt(limit));
                    } else if (response.getIntent().equals(Intent.BID)) {
                        message.setIntent(Intent.BID);
                        message.setData(random.nextBoolean());
                    }
                    convertedMessage = gson.toJson(message);
                    write(convertedMessage);

                    responseStr = in.readLine();
                    response = gson.fromJson(responseStr, Response.class);

                    if (response.getStatus().equals(Status.DENIED)) {
                        seated = false;
                    }
                }

                socket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }
}
