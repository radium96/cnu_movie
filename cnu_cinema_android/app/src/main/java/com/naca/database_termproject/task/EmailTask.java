package com.naca.database_termproject.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailTask extends AsyncTask<String, Integer, String> {
    String sendMsg, receiveMsg;
    String endPoint;

    public EmailTask(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL("https://f28d-122-34-166-172.jp.ngrok.io/"+endPoint);  // 어떤 서버에 요청할지(localhost 안됨.)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
            conn.setDoOutput(true);

            // 서버에 보낼 값 포함해 요청함.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "to=" + strings[0] + "&title=" + strings[1] + "&content=" + strings[2]; // GET방식으로 작성해 POST로 보냄
            osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
            osw.flush();


            // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {    // 통신이 실패한 이유를 찍기위한 로그
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 서버에서 보낸 값을 리턴합니다.
        return receiveMsg;
    }
}
