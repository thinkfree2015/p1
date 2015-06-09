package com.ming800.core.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import org.apache.velocity.app.VelocityEngine;
//import org.apache.velocity.exception.VelocityException;
//import org.springframework.ui.velocity.VelocityEngineUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 邮件发送器
 */
public class MailUtil {

    protected final Log log = LogFactory.getLog(getClass());

    private JavaMailSender javaMailSender;
    //private VelocityEngine velocityEngine;
    private String from;
    private String title;
    private String encoding;
    private String templateLocation;
    private String[] toEmails;
    private Map<String, String> model;

    public boolean send() {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
            message.setFrom(from);
            message.setSubject(title);
            message.setTo(toEmails);
            message.setText(getMessage(), true); // 如果发的不是html内容去掉true参数��
            // message.addInline("myLogo",new ClassPathResource("img/mylogo.gif"));
            // message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            if (log.isWarnEnabled()) {
                log.warn("邮件信息导常! 邮件标题为: " + title);
            }
            return false;
        } catch (MailException me) {
            me.printStackTrace();
            if (log.isWarnEnabled()) {
                log.warn("发送邮件失败! 邮件标题为: " + title);
            }
            return false;
        }
        return true;
    }

    public static void sendCloudTest(String toAddress, String subject, String activeUrl) throws IOException {

        String url = "https://sendcloud.sohu.com/webapi/mail.send.xml";
        HttpClient httpclient = new DefaultHttpClient();
        // httpclient = wrapHttpClient(httpclient);
        HttpPost httpost = new HttpPost(url);

        List nvps = new ArrayList();
        //不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
        nvps.add(new BasicNameValuePair("api_user", "postmaster@norely.sendcloud.org"));//"postmaster@xxx.sendcloud.org"
        nvps.add(new BasicNameValuePair("api_key", "KBSa04r26rIZAgbU"));//"password"
        nvps.add(new BasicNameValuePair("from", "营养通<postmaster@norely.sendcloud.org>"));//"from@sendcloud.org"
        nvps.add(new BasicNameValuePair("to", toAddress));//"to1@sohu-inc.com;to2@126.com"
        nvps.add(new BasicNameValuePair("subject", subject));
        nvps.add(new BasicNameValuePair("html", activeUrl));
        /*<br/><a style="width: 150px; height: 30px; font-size: 20px; color: red" href="" + activeUrl + "">激活帐号<a>。*/

        httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httpost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
    }


    public static void sendMailBySea(String formAddress, String toAddress, String title, String content) throws Exception {

        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.sina.com");
        p.put("mail.smtp.port", "25");
        p.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("smtpaccount@gmail.com", "password");
            }
        };
        Session sendMailSession = Session.getDefaultInstance(p, authenticator);
        Message mailMessage = new MimeMessage(sendMailSession);
        Address from = new InternetAddress(formAddress);
        mailMessage.setFrom(from);
        Address to = new InternetAddress(toAddress);//设置接收人员
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        mailMessage.setSubject(title);//设置邮件标题
        mailMessage.setText(content); //设置邮件内容
        // 发送邮件
        Transport.send(mailMessage);


    }

    /**
     * 邮件模板中得到信息
     *
     * @return 返回特发送的内容
     */
    private String getMessage() {
    /*	try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, encoding, model);
		} catch (VelocityException e) {
			e.printStackTrace();
			log.error("邮件模板读取失败!邮件标题为: " + title);
		}*/
        return "";
    }

    private String[] createToEmail(String to) {
        return new String[]{to};
    }

    public void setToEmail(String to) {
        setToEmails(createToEmail(to));
    }

    /*
        public void setJavaMailSender(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        public void setVelocityEngine(VelocityEngine velocityEngine) {
            this.velocityEngine = velocityEngine;
        }
    */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setModel(Map<String, String> model) {
        this.model = model;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setToEmails(String[] toEmails) {
        this.toEmails = toEmails;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }
}