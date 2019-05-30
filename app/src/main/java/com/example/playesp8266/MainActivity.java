package com.example.playesp8266;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playesp8266.Utils.MobileServer;
import com.example.playesp8266.Utils.SendAsyncTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mIp;       //  IP
    private EditText mPort;     //  端口号
    private EditText mSend;     //  发送内容 不要过长
    private Button btnConnect;


    private Button btnSend;

    private TextView textReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIp = (EditText) findViewById(R.id.text_ip);
        mPort = (EditText) findViewById(R.id.text_port);
        btnConnect = (Button) findViewById(R.id.button_connect);
        btnConnect.setOnClickListener(this);

        mSend = (EditText) findViewById(R.id.text_send);
        btnSend = (Button) findViewById(R.id.button_send);
        btnSend.setOnClickListener(this);

        textReceive = (TextView) findViewById(R.id.tv_receive);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_send:
                //发送部分
                String str = mSend.getText().toString() + '\n';
                //三个参数，第一个为发送的内容，第二个为设置的IP，第三个为设置的端口号，一般情况下这两个是不需要改的
                new SendAsyncTask().execute(str, mIp.getText().toString(), mPort.getText().toString());
                Toast.makeText(MainActivity.this, "已发送", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_connect:
                //接收部分
                //开启服务器
                MobileServer mobileServer = new MobileServer();
                mobileServer.setHandler(handler);
                new Thread(mobileServer).start();
                Toast.makeText(MainActivity.this, "已开启", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String temp = msg.obj.toString();
                    textReceive.setText(temp);
                    Toast.makeText(MainActivity.this, "接收到信息", Toast.LENGTH_LONG).show();
            }
        }
    };
}
