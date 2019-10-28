package server;

import com.google.gson.Gson;
import model.Intent;
import model.Message;
import model.Response;
import model.Status;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private Gson gson;
    private Resources resources;

    private Boolean seated;
    private String id;

    public ServerThread(Socket socket, Resources resources) throws IOException {
        this.socket = socket;
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gson = new Gson();
        this.resources = resources;

        this.seated = false;
    }

    public void run() {
        try {
            String requestStr = in.readLine();
            Message request = gson.fromJson(requestStr, Message.class);

            Response response = new Response();
            response.setStatus(Status.DENIED);

            if (Intent.REQUEST_CHAIR.equals(request.getIntent())) {
                this.id = request.getId();
                seated = resources.giveSeat(new User(id));
                System.out.println("Stigao zahtev za stolicu od korisnika " + request.getId());

                if(seated) {
                    response.setStatus(Status.OK);
                }
            }

            write(gson.toJson(response));

            while(seated && resources.getM() > 0) {
                try {
                    resources.getCyclicBarrier().await();

                    if (id.equals(resources.currentPlayer())) {
                        response.setIntent(Intent.DRAW);
                        response.setData(resources.bulletsLeft());
                    } else {
                        response.setIntent(Intent.BID);
                    }

                    write(gson.toJson(response));

                    requestStr = in.readLine();
                    request = gson.fromJson(requestStr, Message.class);

                    if (request.getIntent() == Intent.DRAW) {
                        int draw = ((Double)request.getData()).intValue();
                        if (draw == 0) {
                            seated = false;
                            response.setStatus(Status.DENIED);
                            resources.setStillGoing(false);
                        }
                        resources.getCyclicBarrier().await();
                        resources.getCyclicBarrier().await();
                        if (!this.seated) {
                            System.out.println("Igrac " + id + " ispada iz igre.");
                            resources.reset(this.id);
                        } else {
                            System.out.println("Igrac " + id + " izvukao je duzi stapic.");
                            resources.advance();
                        }

                    } else {
                        resources.getCyclicBarrier().await();
                        boolean stillGoing = resources.isStillGoing();
                        if ((Boolean)request.getData() == stillGoing) {
                            resources.add(id);
                            response.setData(true);
                        }
                        resources.getCyclicBarrier().await();
                    }
                    write(gson.toJson(response));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            socket.close();

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
