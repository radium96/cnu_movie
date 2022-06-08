package com.naca.cnu_cinema_server;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSendingSecurity {

    public void sendEmail(String to, String title, String content) throws Exception {
        // 메일 관련 정보
        String host = "smtp.naver.com";
        String username = "nachasan1335";
        String password = "********";
        int port = 465;

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String user = username;
            String pass = password;
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        session.setDebug(true);

        // 보내는 사람
        String from = "nachasan1335@naver.com";

        try {
            // 메일 내용 넣을 객체와, 이를 도와주는 Helper 객체 생성
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(from)); // 보내는 사람 세팅
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 받는 사람 세팅
            mimeMessage.setSubject(title);	// 제목 세팅
            mimeMessage.setText(content);	// 내용 세팅

            // 메일 전송
            Transport.send(mimeMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
