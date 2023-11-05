/*reCAPTCHA(구글 api 로봇 검사)
 사용자가 웹페이지를 방문, recaptcha 라이브러리가 페이지에 로드 
로그인 폼에 recaptcha를 적용하려면 로그인 버튼에 특정 속성들을 추가 
사용자가 로그인 버튼을 누르면 라이브러리가 동작하여 로봇검사 챌린지를 실행, 
사용자가 챌린지를 통과하면 recaptcha 라이브러리는 응답 토큰을 생성함
라이브러리는 이 토큰을 인자로 하여 data-callback 속성에서 지정한 함수를 호출함 (frmSubmit)
이 함수에서는 응답 토큰을 폼에 추가하고 폼을 제출하는 작업을 수행함 
이때 응답토큰이 폼의 정보와 함께 같이 서버로 전송되어짐
서버에서는 이 토큰을 받아서 구글에 검증을 요청함 
이 검증 요청을 통해 토큰이 유효한지 확인 
*/

package com.naver.anytime;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class RecaptchaValidationFilter extends OncePerRequestFilter {

	private AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/member/loginProcess", "POST");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (requestMatcher.matches(request)) {
			String recaptchaToken = request.getParameter("grecaptcha");

			// Google reCAPTCHA API 엔드포인트 URL
			String url = "https://www.google.com/recaptcha/api/siteverify";

			// Google reCAPTCHA 시크릿 키
			String secretKey = "6LfKpMkoAAAAABCXYqKqMu_pN_YmSVzVW1fVoEE2";

			// 요청 파라미터 설정
			String parameters = "secret=" + URLEncoder.encode(secretKey, "UTF-8") + "&response="
					+ URLEncoder.encode(recaptchaToken, "UTF-8");

			// API 요청을 위한 연결 설정
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			// 요청 파라미터 전송
			connection.getOutputStream().write(parameters.getBytes("UTF-8"));

			// 응답 받기
			int responseCode = connection.getResponseCode();
			StringBuilder responseBuilder = new StringBuilder();
			// 응답 내용 읽기
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					responseBuilder.append(line);
				}
			} catch (IOException e) {
				// 예외 처리
				e.printStackTrace();
			}

			// 응답 내용 파싱
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(responseBuilder.toString(), JsonObject.class);
			boolean success = jsonObject.get("success").getAsBoolean(); // success 변수 설정
			if (success) {
				filterChain.doFilter(request, response);
			} else {
				//
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
