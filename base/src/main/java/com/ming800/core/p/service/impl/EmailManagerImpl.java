package com.ming800.core.p.service.impl;

import com.ming800.organization.dao.UserDao;
import com.ming800.organization.model.BigUser;
import com.ming800.core.p.service.EmailManager;
import com.ming800.core.util.MD5Encode;
import com.ming800.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-22
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
@Component
public class EmailManagerImpl implements EmailManager {

    @Autowired
    private UserDao userDao;


    private static EmailManagerImpl emailManager = null;

//    public ApplicationContext ctx = null;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public Boolean activeUser(BigUser bigUser, String activeKey) {

        activeKey = activeKey.substring(6, activeKey.length() - 6);

        String strInfo = bigUser.getId() + bigUser.getName();
        MD5Encode md5 = new MD5Encode();
        String md5Str = md5.MD5Encode(strInfo);


        if (md5Str.equals(activeKey)) {
            return true;
        } else {
            return false;
        }


        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendRegisterEmail(BigUser bigUser) {


        String mail_title = "帐号激活";

//        String

        /*try {
            String str="123";
            byte[] plainText = str.getBytes("UTF8");

            //使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

            System.out.println("/n" + messageDigest.getProvider().getInfo());
            //开始使用算法
            messageDigest.update(plainText);
            System.out.println("/nDigest:");
            //输出算法运算结果
            System.out.println(new String(messageDigest.digest(),"UTF8"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        String strInfo = bigUser.getId() + bigUser.getName();


        MD5Encode md5 = new MD5Encode();
        String md5Str = StringUtil.random(6, 4) + md5.MD5Encode(strInfo) + StringUtil.random(6, 4);
        System.out.println(md5Str);


        String mail_content = "欢迎使用本网站,请点击下面的链接激活您的帐号:<a href='http://localhost:8080/reg/activateUser.do?dispatcher=activeUser&userId=" + bigUser.getId() + "&activeKey=" + md5Str;
        EmailManagerImpl email = EmailManagerImpl.getInstance();
//        email.sentEmails(user.getEmail(), mail_title, mail_content);


        //To change body of implemented methods use File | Settings | File Templates.
    }


    private EmailManagerImpl() {
        //获取上下文
        /*String path = new File(EmailManagerImpl.class.getResource("/").getPath()).getParent();
        String filePath = path + "/" + "applicationContext-mail.xml";
        SAXReader reader = new SAXReader();
        Document doc = null;
        try{
            doc = reader.read(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();*/
//        applicationContext = new ClassPathXmlApplicationContext("email.xml");
    }

    /**
     * @function:获得单例
     */
    public static EmailManagerImpl getInstance() {
        if (emailManager == null) {
            synchronized (EmailManagerImpl.class) {
                if (emailManager == null) {
                    emailManager = new EmailManagerImpl();
                }
            }
        }
        return emailManager;
    }


    public void sentEmails(String emails, String subject, String text) {
        //获取JavaMailSender bean
        JavaMailSender sender = (JavaMailSender) applicationContext.getBean("mailSender");
        //SimpleMailMessage只能用来发送text格式的邮件
        SimpleMailMessage mail = new SimpleMailMessage();
        String email[] = emails.split(";");
        for (int i = 0; i < email.length; i++) {
            try {
                mail.setTo(email[i]);//接受者
                mail.setFrom("mingribo800@163.com");
                mail.setSubject(subject);//主题
                mail.setText(text);//邮件内容
                sender.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
