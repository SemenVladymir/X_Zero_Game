package com.example.x_zero_game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    public static final int PORT = 8080;
    public static ArrayList<ServerSomthing> serverList = new ArrayList<>(); // список всех нитей

    private static boolean symb = true;

    public static void main(String[] args) throws UnknownHostException, IOException {
        ServerSocket server = new ServerSocket(PORT);
//        var talk = new SereverTalk(serverList);
//        talk.start();
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    if (serverList.size() < 2) {
                        serverList.add(new ServerSomthing(socket, symb ? "X" : "0")); // добавить новое соединенние в список
                        //talk.Datachange(serverList);
                        symb = !symb;
                    }
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его при завершении работы:
                    socket.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            server.close();
        }
    }

    public static void Talk(ArrayList<ServerSomthing> data) throws IOException {
        String text;
        Scanner scanner = new Scanner(System.in);
        do {
            text = scanner.next();
            if(!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).send(text);
                }
            }
        }while(!Objects.equals(text, "exit"));
    }
}