package com.example.playesp8266.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Layne_Yao on 2017/5/12.
 * CSDN：http://blog.csdn.net/Jsagacity
 */

public class SendAsyncTask extends AsyncTask<String, Void, Void> {

    //连接ESP8266的IP和端口号，IP是通过指令在单片机开发板查询到，而端口号可以自行设置，也可以使用默认的.

    private Socket client = null;
    private PrintStream out = null;

    @Override
    protected Void doInBackground(String... params) {
        String str = params[0];
        try {
            Log.d("Test", "doInBackground: " + params[1] +"   "+ params[2]);
            //通过EditText设置IP和端口
            client = new Socket(params[1], Integer.parseInt(params[2]));
            client.setSoTimeout(5000);
            // 获取Socket的输出流，用来发送数据到服务端
            out = new PrintStream(client.getOutputStream());
            out.print(str);
            out.flush();

            if (client == null) {
                return null;
            } else {
                out.close();
                client.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
