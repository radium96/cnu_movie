package com.naca.database_termproject.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailTask extends AsyncTask<String, Integer, String> {

    // Spring서버로 보낼 메세지와 받을 메세지를 저장한다.
    String sendMsg, receiveMsg;
    // 접속할 주소를 저장한다.
    String endPoint;

    // 생성자를 통해 접속할 주소의 값을 받아온다.
    public EmailTask(String endPoint) {
        this.endPoint = endPoint;
    }

    // 보낼 값이 여러개일 때, 배열을 통해서 값을 받도록 한다.
    @Override
    protected String doInBackground(String... strings) {
        try {
            // 서버에서 보내온 값을 한 줄씩 읽어올 String객체이다.
            String str;
            // Spring서버가 연결되어있는 URL을 설정한다.
            // 해당 주소는 ngrok이라는 터널링 프로그램으로 생성된 주소로 열려있는 pc의 localhost:10000으로 연결된다.
            // ngrok의 정책상 서버를 열 때 마다 주소 앞 최상단 4글자를 변경해줘야 하므로 데모환경에서는 주소값이 변경될 수 있다.
            URL url = new URL("https://f28d-122-34-166-172.jp.ngrok.io/"+endPoint);
            // url의 http환경으로 접속을 요청한다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 접속의 환경설정을 진행한다.
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 서버로 값을 보낼 OutputStreamWriter를 설정한다.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            // 이메일에 대한 정보를 하나의 문자열로 저장한다.
            sendMsg = "to=" + strings[0] + "&title=" + strings[1] + "&content=" + strings[2];
            // OutputStreamWriter에 정보에 대한 문자열을 전송한다.
            osw.write(sendMsg);
            osw.flush();

            // Spring서버에 존재하는 jsp와 통신하여 서버에서 보낸 값을 받아온다.
            // 만약 통신에 성공한다면 값을 받아온다.
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 미리 설정된 url을 통해 값을 받아올 InputStreamReader를 설정한다. 문자열 형식은 UTF-8로 받는다.
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                // 연결된 InputStreamReader를 통해 값을 받아올 BufferedReader와 StringBuilder를 선언하고 초기화한다.
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder sb = new StringBuilder();
                // BufferedReader를 통해 값을 받아 str에 전달하고 StringBuilder에 전달한다.
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                // StringBuilder에 저장된 값을 반환하기 위해 미리 선언된 String 객체에 저장한다.
                receiveMsg = sb.toString();
            }
            // 만약 통신에 실패했다면 Android Studio에 로그를 출력하여 확인한다.
            else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 최종적으로 서버에서 받은 값을 반환해준다.
        return receiveMsg;
    }
}
