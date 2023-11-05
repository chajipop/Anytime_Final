package com.naver.anytime.task;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.naver.anytime.service.BoardService;

@Service
@EnableScheduling //아래의 클래스를 스케즐링 목적으로 사용하도록 하겠다고 명시합니다. @Configuration와 같이 사용해야 합니다.
@Configuration //이 애노테이션을 사용하지 않으면 스케줄링이 동작하지 않습니다.
public class FileCheckTask {
	private static final Logger logger = LoggerFactory.getLogger(FileCheckTask.class);
	
	@Value("${my.savefolder}")
	private String saveFolder;
	
	private BoardService boardService;
	
	@Autowired
	public FileCheckTask(BoardService boardService) {
		this.boardService=boardService;
	}
	

}
