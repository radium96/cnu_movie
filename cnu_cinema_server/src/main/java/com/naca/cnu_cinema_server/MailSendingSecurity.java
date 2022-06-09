package com.naca.cnu_cinema_server;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

// 계정 보안상의 이유로 실제 사용하는 클래스가 아니라 해당 클래스를 복사하고 비밀번호만 제거하여 제출하였다.
public class MailSendingSecurity {

    // 메일을 보낼 이메일, 메일 제목, 메일 내용을 받는다.
    public void sendEmail(String to, String title, String content) throws Exception {
        // 네이버 메일을 통해 메일 전송을 진행하기위해 메일 전송 서버로 접속한다.
        String host = "smtp.naver.com";
        // 메일 전송에 이용할 메일의 아이디를 저장한다.
        String username = "nachasan1335";
        // 메일 전송에 이용할 메일 아이디의 비밀번호를 저장한다.
        // 보안상의 이유로 빈 문자열로 대체한다. 데모에서는 실제 비밀번호를 입력하여 연결하였다.
        String password = "";
        // 네이버 메일 전송 서버의 포트를 저장한다.
        int port = 465;

        // 서버와 연결할 때 필요한 설장을 진행한다.
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", host);

        // 받아온 정보와 설정한 메일을 바탕으로 메일 전송을 진행할 세선을 구성한다.
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            // 메일을 전송할 계정에 대해 로그인을 진행한다.
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);

        // 보내는 메일로 어떤 값을 넣을지 설정한다.
        // 실제 전송하는 메일 주소로 구성하였다.
        String from = "nachasan1335@naver.com";

        // 보낼 메일의 정보를 설정한다.
        try {
            // 메일의 제목과 내용을 넣을 객체와 이 과정을 도와주는 Helper 객체 생성한다.
            MimeMessage mimeMessage = new MimeMessage(session);

            // 보내는 사람의 메일을 설정한다.
            mimeMessage.setFrom(new InternetAddress(from));
            // 받는 사람의 메일을 설정한다.
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 메일의 제목을 설정한다.
            mimeMessage.setSubject(title);
            // 메일의 내용을 설정한다.
            mimeMessage.setText(content);

            // 모든 설정이 완료된 메일을 세션을 통해 로그인한 메일을 통하여 전송한다.
            Transport.send(mimeMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
