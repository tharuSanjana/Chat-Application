package org.example.client;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class Client {
    private Socket socket;
    private List<Client> clients;
    private DataInputStream dataInputStream ;
    private DataOutputStream dataOutputStream;


    private String msg = "";

    public Client(Socket socket, List<Client> clients) throws IOException {

            this.socket = socket;
            this.clients = clients;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());


        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    while (socket.isConnected()) {
                        msg = dataInputStream.readUTF();
                        for (Client client : clients) {
                            if (client.socket.getPort() != socket.getPort()) {
                                client.dataOutputStream.writeUTF(msg);
                                client.dataOutputStream.flush();
                            }
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

