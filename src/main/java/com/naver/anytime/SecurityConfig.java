package com.naver.anytime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;



import com.naver.security.CustomAccessDeniedHandler;
import com.naver.security.CustomUserDetailsService;
import com.naver.security.LoginFailHandler;
import com.naver.security.LoginSuccessHandler;


@EnableWebSecurity //스프링과 시큐리티 결합
@Configuration	
public class SecurityConfig{
	
	@Autowired
	private DataSource datasource;
	
	//SecurityFilterChain 를 빈으로 등록하는 방식을 권장
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/resources/**/**").permitAll()
     			 // Main 
		        .antMatchers("/main/home").permitAll()
     			
     			 // Member
     			.antMatchers("/member/login").permitAll()
     			.antMatchers("/member/loginProcess").permitAll()
				.antMatchers("/member/register").permitAll()
				.antMatchers("/member/agreement").permitAll()
				.antMatchers("/member/email").permitAll()
				.antMatchers("/member/emailsend").permitAll()
				.antMatchers("/member/join").permitAll()
				.antMatchers("/member/idcheck").permitAll()
				.antMatchers("/member/mailcheck").permitAll()
				.antMatchers("/member/nicknamecheck").permitAll()
				.antMatchers("/member/joinProcess").permitAll()
				.antMatchers("/member/forgotid").permitAll()
				.antMatchers("/member/forgotid_email").permitAll()
				.antMatchers("/member/forgotpwd").permitAll()
				.antMatchers("/member/forgotpwd_mailcheck").permitAll()
				.antMatchers("/member/forgotpwd_mailsend").permitAll()
				.antMatchers("/member/forgotpwd_result").permitAll()
				.antMatchers("/member/forgotpwd_resultProcess").permitAll()
				
				 // Member_after_login
				.antMatchers("/my").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/password").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/update").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updateProcess").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/boardlist").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/delete").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/deleteProcess").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/certificate").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/certificate_mailsend").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/certificate_mailcheck").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/certificateProcess").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")

				 // Admin
				.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/boardAdmin").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/updateBoardStatus").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/updateBoardStatusImmediately").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/boardtotal").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/reportAdmin").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/reportListAdmin").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/reportTotal").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/reportProcess").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/adminNotice").access("hasRole('ROLE_ADMIN')")  
			    .antMatchers("/adminNoticeList").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/notice").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
			    .antMatchers("/noticeList").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
			     // Board 
				.antMatchers("/submenulist").permitAll() //바꿀게요
				.antMatchers("/create").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/getboardcontent").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updateboardcontent").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/managercheck").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/deleteboard").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updatemanagerboard").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
				.antMatchers("/hotpost").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/bestpost").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
			

				
			    // Calendar
				.antMatchers("/calendar").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/calendarlist").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/calendaradd").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/calendarupdate").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/calendardropupdate").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/calendardelete").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
			
				
				// Comment
				.antMatchers("/comment/list").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/comment/add").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/comment/update").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/comment/delete").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/comment/reply").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
			
				// Credit
				.antMatchers("/calculator").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/getsemester_detail").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updatesemester_detail").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updateGraduateCredit").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/gettimetable").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/gettimetable_detail").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
				// Message
				.antMatchers("/message").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/messagelist").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/messagelastlist").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/sendmessage").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
				
			    // Post
				.antMatchers("/post/detail").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/write").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/updateGet").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/updatePost").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/delete").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/likePost").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/list").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/post/search").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
				// Report
				.antMatchers("/report").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
			    
				// School
				.antMatchers("/{schoolDomain}").permitAll()
				
				
				// Scrap
				.antMatchers("/scrap").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				
				// TimeTable
				.antMatchers("/timetable").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/changeName").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/getTimetableByUserIdAndSemester").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/createNewTimeTable").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/updateTimetable").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/loadTimetableDetails").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/addSubject").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/deleteTimetable").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
				.antMatchers("/deleteSubject").access("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')");
				
				
		
		// loginprocess
		http.formLogin().loginPage("/member/login")
				        .loginProcessingUrl("/member/loginProcess")
				        .usernameParameter("login_id")
				        .passwordParameter("password")
				        .successHandler(loginSuccessHandler())
				        .failureHandler(loginFailHandler());
		
		
		/*
		* (1) logoutSuccessUrl : 로그아웃 후 이동할 주소
		* (2) logoutUrl : (여기서 처리하기 때문에 컨트롤러 logout 제거 합니다. post방식을 요구합니다.)
		* (3) invalidateHttpSession : 로그아웃시 세션 속성들 제거
		* (4) deleteCookies : 쿠키 제거
		*/
		http.logout().logoutSuccessUrl("/member/login")
		.logoutUrl("/member/logout")
		.invalidateHttpSession(true)
		.deleteCookies("autologin", "JSESSION_ID");
		
		http.rememberMe()
	      .rememberMeParameter("autologin")
	      .rememberMeCookieName("autologin")
	      .userDetailsService(customUserService())
	      .tokenValiditySeconds(2419200)
	      .tokenRepository(tokenRepository());
		
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		
		//reCAPTCHA 
		http.addFilterBefore(recaptchaValidationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
	
	

	//추가
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//로그인 성공시 실행할 객체 생성
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler(); //패키지 com.naver.security에 존재하는 모든 클래스 파일에 @Service를 삭제합니다.
	}
	
	@Bean
    public UserDetailsService customUserService() {
       return new CustomUserDetailsService(); //패키지 com.naver.security에 존재하는 모든 클래스 파일에 @Service를 삭제합니다.
    }
  
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }
  
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
       return new CustomAccessDeniedHandler();
    }
    
    @Bean
    public AuthenticationFailureHandler loginFailHandler() {
       return new LoginFailHandler();
    }
    
    @Bean
    public PersistentTokenRepository tokenRepository(){
    	//PersistentTokenRepository의 구현체인 JdbcTokenRepositoryImpl 클래스 사용합니다.
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(datasource); //dataSource 주입
        return jdbcTokenRepository;
    }
    
    //reCAPTCHA
    @Bean
    public RecaptchaValidationFilter recaptchaValidationFilter() {
    	return new RecaptchaValidationFilter();
    	
    }
    
}
