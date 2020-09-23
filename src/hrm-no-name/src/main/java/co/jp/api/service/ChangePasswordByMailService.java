package co.jp.api.service;

import co.jp.api.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class ChangePasswordByMailService {
    private final long CODE_EXPIRATION = 24*60*60*1000L;
    @Autowired
    JavaMailSender emailSender;
    @Autowired
    UserDao userDao;
    public void sendMail(String to,String subject,String text){
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }
    public String generateCode(String email){
        String code = generateRandomHexToken(3);

        return code;
    }
    public void setCode(String email,String code){
        try{
            System.out.println("email to :" +email);
            Date now = new Date();
            userDao.setCode(email,code,now, new Date(now.getTime()+CODE_EXPIRATION));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }

}
