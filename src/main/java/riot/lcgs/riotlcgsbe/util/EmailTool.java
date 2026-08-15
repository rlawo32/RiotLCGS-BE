package riot.lcgs.riotlcgsbe.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class EmailTool {

    private static final String USERNAME = "gandi779@gmail.com";
    private static final String PASSWORD = "vcjttztqedaugmbd";

    private static Session sendMailSession() {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
    }

    public static void sendMessage_Image(String messageType, Map<String, Object> messageInfo) {

        String targetEmail = "rlawo32@naver.com";

        try {
            Message message = new MimeMessage(sendMailSession());

            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress(USERNAME, "R2 Auto ImageUpload Scheduler"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
            if(messageType.equals("S")) {
                message.setSubject("[LCGS-BE] R2 이미지 업로드 시작 안내 메일");
                message.setText("신규 버전 " + (String) messageInfo.get("version") + " 이미지 업로드를 시작했습니다.");
            }
            if(messageType.equals("E")) {
                message.setSubject("[LCGS-BE] R2 이미지 업로드 종료 안내 메일");
                long elapsedMs = (long) messageInfo.get("elapsedMillis");
                long second = elapsedMs / 1000;
                long minute = second / 60;
                second %= 60;
                message.setText("이미지 업로드 완료 - 걸린 시간 : " + minute + "분 " + second + "초 / 업로드 수 : " + (int) messageInfo.get("uploadedCount"));
            }

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
