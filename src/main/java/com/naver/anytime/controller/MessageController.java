package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Message;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.MessageService;
import com.naver.anytime.service.SchoolService;

@RestController
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private MessageService messageService;
	private MemberService memberService;
	private BoardService boardService;
	private SchoolService schoolService;
	
	@Autowired
	public MessageController(MessageService messageService, MemberService memberService, BoardService boardService, SchoolService schoolService) {
		this.messageService = messageService;
		this.memberService = memberService;
		this.boardService = boardService;
		this.schoolService = schoolService;
	}
	
	@RequestMapping(value = "/message")
	@ResponseBody
	public ModelAndView message(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv	
			) {
		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("message/Message");
		mv.addObject("school", school);
		return mv;
	}
	
	@RequestMapping(value = "/messagelist")
	@ResponseBody
	public List<Message> MessageList(
			@RequestParam("messageallid") int messageall_id,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기		
		
		System.out.println("로그인 한 유저 아이디 [" + login_id + "] 로그인 한 유저 번호 [" + user_id + "]");
		
		List<Message> messageList = messageService.getMessageList(user_id, messageall_id);
		
		for(Message message : messageList) {
			if(message.getSENDER() == user_id) {
				message.setDIRECTION(2);
			} else if (message.getRECEIVER() == user_id){
				message.setDIRECTION(1);
			}
		}
		
		if(messageList != null) {
			System.out.println("==메시지 출력 성공==");
		}else {
			System.out.println("==메시지 출력 실패==");
		}
		
		return messageList;
	}
	
	@RequestMapping(value = "/messagelastlist")
	@ResponseBody
	public List<Message> MessageLastList(
			Principal principal
			) {
		String login_id = principal.getName();					//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기		
		
		System.out.println("로그인 한 유저 아이디 [" + login_id + "] 로그인 한 유저 번호 [" + user_id + "]");
		
		List<Message> messageLastList = messageService.getMessageListROWNUM(user_id);
		
		for(Message message : messageLastList) {
				
			int anonymouscheck = boardService.getBoardAnonymous2(message.getPOST_COMMENT_ID());
				
			if(anonymouscheck == 0) {
				if(message.getSENDER() == user_id) {
					message.setNickname(memberService.getNickName2(message.getRECEIVER()));
					if(memberService.getStatusCheck2(message.getRECEIVER()) == 0) {
						message.setNickname("알 수 없음");
					}
				}else{
					message.setNickname(memberService.getNickName2(message.getSENDER()));
					if(memberService.getStatusCheck2(message.getSENDER()) == 0) {
						message.setNickname("알 수 없음");
					}
				}		
			}else {
				message.setNickname("익명");
			}
				

		}
		
		
		
		if(messageLastList != null) {
			System.out.println("==메시지 출력 성공==");
		}else {
			System.out.println("==메시지 출력 실패==");
		}
		
		return messageLastList;
	}	
	
	
	@RequestMapping(value = "/sendmessage")
	@ResponseBody
	public int sendMessage(
			@RequestParam(value = "post_id", required = false, defaultValue = "0") int post_id,
			@RequestParam(value = "comment_id", required = false, defaultValue = "0") int comment_id,
			@RequestParam(value = "messageall_id", required = false, defaultValue = "0") int messageall_id,
			@RequestParam("content") String content,
			Principal principal
			) {
		int sendResult = 0;
		int messageinsert = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int sender_user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		
		
		if(post_id > 0) {
			int receiver_user_id = memberService.getUserIdConversion(post_id);
			int check = messageService.isMessageAllIdPresent(sender_user_id,receiver_user_id);
			
			System.out.println("(1) - 체크 [" + check + "]");
			if(check == 0) {
				int messageall = messageService.insertMessageAllId(post_id, sender_user_id, receiver_user_id);
				System.out.println("(2) - 쪽지 관리번호 insert 체크 [" + messageall + "]");
			}
			messageall_id = messageService.isMessageAllIdPresent2(sender_user_id,receiver_user_id);
			System.out.println("(3) - 쪽지 관리번호 체크 [" + messageall_id + "]");
			messageinsert = messageService.insertMessage2(messageall_id, sender_user_id, receiver_user_id, content);
			System.out.println("(4) - 메시지 insert 체크 [" + messageinsert + "]");
		} else if (comment_id > 0) {
			int receiver_user_id = memberService.getUserIdConversion(comment_id);
			int check = messageService.isMessageAllIdPresent(sender_user_id,receiver_user_id);
			
			System.out.println("(1) - 체크 [" + check + "]");
			if(check == 0) {
				int messageall = messageService.insertMessageAllId(comment_id, sender_user_id, receiver_user_id);
				System.out.println("(2) - 쪽지 관리번호 insert 체크 [" + messageall + "]");
			}
			messageall_id = messageService.isMessageAllIdPresent2(sender_user_id,receiver_user_id);
			System.out.println("(3) - 쪽지 관리번호 체크 [" + messageall_id + "]");
			messageinsert = messageService.insertMessage2(messageall_id, sender_user_id, receiver_user_id, content);
			System.out.println("(4) - 메시지 insert 체크 [" + messageinsert + "]");

		} else if (messageall_id > 0) {
			int receiver_user_id = messageService.getUserIdConversion2(sender_user_id,messageall_id);
			messageinsert = messageService.insertMessage2(messageall_id, sender_user_id, receiver_user_id, content);

		}

		
		if(messageinsert == 1) {
			sendResult = 1;
		}
		
		System.out.println("(5) - 메시지 최종 완료 체크 [" + sendResult + "]");
		return sendResult;
	}
	
}
