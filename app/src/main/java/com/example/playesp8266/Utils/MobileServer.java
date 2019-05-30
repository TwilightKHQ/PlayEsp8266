package com.example.playesp8266.Utils;

import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MobileServer implements Runnable {
    private ServerSocket server;
    private DataInputStream in;
    private byte[] receive;

    private Handler handler = new Handler();

    public MobileServer() {}

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        try {
            //5000是手机端开启的服务器的端口号，ESP8266进行TCP连接时使用的端口，而IP也是通过指令查询的联入设备的IP
            server = new ServerSocket(5000);
            while (true) {
                Socket client = server.accept();
                in = new DataInputStream(client.getInputStream());
                receive = new byte[50];
                in.read(receive);
                in.close();

                Message message = new Message();
                message.what = 1;
                message.obj = new String(receive);
                handler.sendMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //下面这一段没怎么看懂，只知道当已有APP开启过服务器，再点击就会崩溃
//        try {
//            server.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}