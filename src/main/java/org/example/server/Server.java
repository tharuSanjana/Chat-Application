package org.example.server;

import org.example.client.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void server() throws IOException {
        ArrayList<Client> clientSet = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket socket;

        while (true) {
            System.out.println("Waiting for Client ...");
            socket = serverSocket.accept();

            System.out.println("Client connected");
            Client client = new Client(socket, clientSet);
            clientSet.add(client);

        }
    }

}



