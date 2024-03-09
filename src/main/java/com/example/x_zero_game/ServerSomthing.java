package com.example.x_zero_game;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class ServerSomthing extends Thread {
    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    private String symbol;
    private String name;

    public ServerSomthing(Socket inpsocket, String symb) throws IOException, SQLException {
        symbol = symb;
        socket = inpsocket;
        System.out.println(socket.getPort());
        // если потоку ввода/вывода приведут к генерированию исключения, оно пробросится дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        SQLConnection.Start();
        start(); // вызываем run()

    }
    @Override
    public void run() {
        String word;
        try {
            while (true) {
                word = in.readLine();
                if (word.equals("stop")) break;
                if (word.equals("GetSymbol")) {
                    this.send(symbol);
                    continue;
                }
                if (Objects.equals(word.split(":")[0], "name")) {
                    name = word.split(":")[1];
                    continue;
                }
                if (word.contains("/")) {
                    for (ServerSomthing vr : Server.serverList) {
                        if (vr != this)
                            vr.send(word); // отослать принятое сообщение с
                        // привязанного клиента всем остальным включая его
                    }
                }
            }

        } catch (IOException e) {
        }
    }

    public void send(String msg) throws IOException {
        try {
        if(name!=null)
            SQLConnection.Save(name, symbol, msg, String.valueOf(new Date()));
        out.write(msg+"\n");
        out.flush();
        } catch (IOException e) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
