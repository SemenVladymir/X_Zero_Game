package com.example.x_zero_game;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public String symb;

    public Client() throws IOException {
        clientSocket = new Socket("localhost", 8080); // этой строкой мы запрашиваем
        //  у сервера доступ на соединение
        reader = new BufferedReader(new InputStreamReader(System.in));
        // читать соообщения с сервера
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // писать туда же
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public String GetSymbol() throws IOException{
        String symb = "";
        try {
            out.write("GetSymbol\n"); // отправляем сообщение на сервер
            out.flush();
            symb = in.readLine();
            System.out.println(symb);
        } catch (IOException e) {
            System.err.println(e);
        }
        return symb;
    }

    public static void main(String[] args) {
    }

    public int [] Output (String data) throws IOException {
        int [] idata = new int[2];
        try {
            out.write(data+"\n"); // отправляем сообщение на сервер
            out.flush();
            String sdata = in.readLine();
            String [] tmp = sdata.split("/");
            for (int i=0; i < tmp.length; i++)
                idata[i] = Integer.parseInt(tmp[i]);
        } catch (IOException e) {
            System.err.println(e);
        }
        return idata;
    }

    public int [] Input (){
        int [] idata = new int[2];
        try {
            String sdata = in.readLine();
            String [] tmp = sdata.split("/");
            for (int i=0; i < tmp.length; i++)
                idata[i] = Integer.parseInt(tmp[i]);
        } catch (IOException e) {
            System.err.println(e);
        }
        return idata;
    }

    public void Send(String data) throws IOException {
        out.write(data+"\n"); // отправляем сообщение на сервер
        out.flush();
    }
}
