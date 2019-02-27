package com.logui.arghasen.socketapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public EditText editText_IP, editText_Port, editText_user;
    public String ip;
    public int port = 0;
    public boolean flag;
    public Socket client;
    Handler handler = new Handler();
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_IP = findViewById(R.id.edittext_IP);
        editText_Port = findViewById(R.id.edittext_Port);

        Thread mythread = new Thread(new MyServer());
        mythread.start();
    }

    class MyServer implements Runnable {
        ServerSocket ss;
        Socket mysocket;
        DataInputStream dis;
        DataOutputStream dos;
        PrintWriter pr;
        @Override
        public void run() {
            try {
                ss = new ServerSocket(9009);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Waiting for sender", Toast.LENGTH_SHORT).show();
                    }
                });
                while (true) {
                    mysocket = ss.accept();
                    dos = new DataOutputStream(mysocket.getOutputStream());
                    pr = new PrintWriter(dos);
                    pr.write("Hello World");
                    pr.flush();
                    pr.close();
                    dis = new DataInputStream(mysocket.getInputStream());
                    message = dis.readUTF();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Message Received: " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}