package com.naver.anytime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Message;
import com.naver.anytime.mybatis.mapper.MessageMapper;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageMapper dao;

	public MessageServiceImpl(MessageMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<Message> getMessageList(int user_id, int messageall_id) {
		return dao.getMessageList(user_id, messageall_id);
	}

	@Override
	public List<Message> getMessageListROWNUM(int user_id) {
		return dao.getMessageListROWNUM(user_id);
	}

	@Override
	public int getUserIdConversion2(int sender_user_id, int messageall_id) {
		return dao.getUserIdConversion2(sender_user_id, messageall_id);
	}

	@Override
	public int insertMessage(int check, int messageall_id, int sender_user_id, int receiver_user_id, String content) {
		return dao.insertMessage(check, messageall_id, sender_user_id, receiver_user_id, content);
	}

	@Override
	public int isMessageAllIdPresent(int sender_user_id, int receiver_user_id) {
		return dao.isMessageAllIdPresent(sender_user_id, receiver_user_id);
	}

	@Override
	public int insertMessageAllId(int num, int sender_user_id, int receiver_user_id) {
		return dao.insertMessageAllId(num, sender_user_id, receiver_user_id);
	}

	@Override
	public int insertMessage2(int messageall_id, int sender_user_id, int receiver_user_id, String content) {
		return dao.insertMessage2(messageall_id, sender_user_id, receiver_user_id, content);
	}

	@Override
	public Integer isMessageAllIdPresent2(int sender_user_id, int receiver_user_id) {
		return dao.isMessageAllIdPresent2(sender_user_id, receiver_user_id);
	}

	@Override
	public void insertMessageAllWithSenderAndReceiver(int admin_id, int reported_user_id) {
		dao.insertMessageAllWithSenderAndReceiver(admin_id, reported_user_id);

	}
}
