package com.naver.anytime.task;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

	@Value("${my.sendfile}")
	 private String sendfile;
	
	private JavaMailSenderImpl mailSender;

	@Autowired
	public SendMail(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	// 회원가입시 메일 본인 인증
	public void sendAuthEmail(String to, String subject, String authCode) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분");
				String now = LocalDateTime.now().format(formatter);
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("chaji281@naver.com"); // 보내는 사람의 이메일 주소
				helper.setTo(to);
				helper.setSubject(subject);
				// 로고 이미지를 이메일에 첨부

				String content = "<div>"
					    + "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
					        + "<tbody>"
					            + "<tr>"
					                + "<td>"
					                    + "<table border='0' cellpadding='0' cellspacing='0' width='600'>"
					                        + "<tbody>"
					                            + "<tr>"
					                                + "<td style='padding: 16px 16px 0 16px;'>"
					                                    + "<a href='${pageContext.request.contextPath}/member/login' target='_blank' style='text-decoration: none;' rel='noreferrer noopener'>"
					                                        + "<img src='cid:logo' width='28' height='30' loading='lazy'>"
					                                    + "</a>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 16px 16px 0 16px; line-height: 30px; color: #292929; font-size: 24px;'>"
					                                    + "<b style='color:  #7869E6;'>애니타임</b>"
					                                    + "<br>인증번호 발송 안내</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px;'>"
					                                    + "<span style='display: inline-block; width: 48px; height: 1px; background-color: #292929;'></span>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px; line-height: 20px; color: #292929; font-size: 14px;'>안녕하세요, 애니타임입니다.<br><br>"
					                                    + now + "에 인증번호 발송 요청이 있었습니다.<br>"
					                                    + "본 이메일에 해당하는 인증번호는 <strong>" + authCode + "</strong> 입니다.<br><br>"
					                                    + "<a href='${pageContext.request.contextPath}/member/login' target='_blank' rel='noreferrer noopener'>애니타임 바로가기</a>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px;'>"
					                                    + "<span style='display: inline-block; width: 48px; height: 1px; background-color: #292929;'></span>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 16px 16px; line-height: 18px; color: #737373; font-size: 12px;'>"
					                                    + "* 본 메일은 발신 전용 메일이므로 답장을 받을 수 없는 메일입니다.<br>"
					                                    + "* 본 메일은 광고성 정보 수신 동의 여부와 관계없이, 운영상 안내 목적으로 발송되는 메일입니다.<br>"
					                                    + "* 본 메일은 회원가입 시 입력한 이메일 주소로 발송된 메일입니다.<br><br>"
					                                    + "메일에 포함된 정보는 지정된 수신인에게만 발송되는 것으로 보안을 유지해야 하는 정보와 법률상 및 기타 사유로 공개가 금지된 정보가 포함되어 있을 수 있습니다.<br>"
					                                    + "귀하가 지정된 수신인이 아니라면 본 메일에 포함된 정보의 전부 또는 일부를 무단으로 보유, 사용하거나 제3자에게 공개, 복사, 전송, 배포해서는 안 됩니다.<br>"
					                                    + "<b>Copyright ⓒ 애니타임. All Rights Reserved.</b>"
					                                + "</td>"
					                            + "</tr>"
					                        + "</tbody>"
					                    + "</table>"
					                + "</td>"
					            + "</tr>"
					        + "</tbody>"
					    + "</table>"
					+ "</div>";
				
				helper.setText(content, true);
				FileSystemResource file = new FileSystemResource(new File(sendfile));
				helper.addInline("logo", file);
			}
		};
		mailSender.send(mp);
		logger.info("메일 전송했습니다.");
	}

	// 아이디 찾기 이메일
	public void sendFindIdEmail(String to, String subject, String foundId) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분");
				String now = LocalDateTime.now().format(formatter);
				
				
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("chaji281@naver.com"); // 보내는 사람 이메일 주소
				helper.setTo(to);
				helper.setSubject(subject);

				String content = "<div>"
					    + "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
					        + "<tbody>"
					            + "<tr>"
					                + "<td>"
					                    + "<table border='0' cellpadding='0' cellspacing='0' width='600'>"
					                        + "<tbody>"
					                            + "<tr>"
					                                + "<td style='padding: 16px 16px 0 16px;'>"
					                                    + "<a href='${pageContext.request.contextPath}/member/login' target='_blank' style='text-decoration: none;' rel='noreferrer noopener'>"
					                                        + "<img src='cid:logo' width='32' height='37' loading='lazy'>"
					                                    + "</a>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 16px 16px 0 16px; line-height: 30px; color: #292929; font-size: 24px;'>"
					                                    + "<b style='color:  #7869E6;'>애니타임</b>"
					                                    + "<br>아이디 찾기 안내</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px;'>"
					                                    + "<span style='display: inline-block; width: 48px; height: 1px; background-color: #292929;'></span>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px; line-height: 20px; color: #292929; font-size: 14px;'>안녕하세요, 애니타임입니다.<br><br>"
					                                    + now + "에 아이디 찾기 요청이 있었습니다.<br>"
					                                    + "본 이메일에 해당하는 아이디는 <strong>" + foundId + "</strong> 입니다.<br><br>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 0 16px;'>"
					                                    + "<span style='display: inline-block; width: 48px; height: 1px; background-color: #292929;'></span>"
					                                + "</td>"
					                            + "</tr>"
					                            + "<tr>"
					                                + "<td style='padding: 32px 16px 16px 16px; line-height: 18px; color: #737373; font-size: 12px;'>"
					                                    + "* 본 메일은 발신 전용 메일이므로 답장을 받을 수 없는 메일입니다.<br>"
					                                    + "* 본 메일은 광고성 정보 수신 동의 여부와 관계없이, 운영상 안내 목적으로 발송되는 메일입니다.<br>"
					                                    + "* 본 메일은 회원가입 시 입력한 이메일 주소로 발송된 메일입니다.<br><br>"
					                                    + "메일에 포함된 정보는 지정된 수신인에게만 발송되는 것으로 보안을 유지해야 하는 정보와 법률상 및 기타 사유로 공개가 금지된 정보가 포함되어 있을 수 있습니다.<br>"
					                                    + "귀하가 지정된 수신인이 아니라면 본 메일에 포함된 정보의 전부 또는 일부를 무단으로 보유, 사용하거나 제3자에게 공개, 복사, 전송, 배포해서는 안 됩니다.<br>"
					                                    + "<b>Copyright ⓒ 애니타임. All Rights Reserved. </b>"
					                                + "</td>"
					                            + "</tr>"
					                        + "</tbody>"
					                    + "</table>"
					                + "</td>"
					            + "</tr>"
					        + "</tbody>"
					    + "</table>"
					+ "</div>";
				
				
			
				
				helper.setText(content, true);
				FileSystemResource file = new FileSystemResource(new File(sendfile));
				helper.addInline("logo", file);

			}
		};
		try {
			mailSender.send(mp);
			logger.info("아이디 찾기 메일 전송했습니다. ");
		} catch (MailException e) {
			logger.error("메일 전송 실패", e);
		}
	}

}