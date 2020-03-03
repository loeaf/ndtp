package ndtp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ndtp.domain.CivilVoice;
import ndtp.domain.Key;
import ndtp.domain.UserSession;
import ndtp.service.CivilVoiceService;

@Slf4j
@RestController
@RequestMapping("/civil-voices")
public class CivilVoiceRestController {

	@Autowired
	private CivilVoiceService civilVoiceService;

	/**
	 * 시민참여 등록
	 * @param request
	 * @param civilVoice
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public Map<String, Object> insert(HttpServletRequest request, @Valid @ModelAttribute CivilVoice civilVoice, BindingResult bindingResult) {
		log.info("@@@@@ insert-group civilVoice = {}", civilVoice);

		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;

		try {
			UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());

			if(bindingResult.hasErrors()) {
				message = bindingResult.getAllErrors().get(0).getDefaultMessage();
				log.info("@@@@@ message = {}", message);
				result.put("statusCode", HttpStatus.BAD_REQUEST.value());
				result.put("errorCode", errorCode);
				result.put("message", message);
	            return result;
			}

			civilVoice.setUserId(userSession.getUserId());
			if(civilVoice.getLongitude() != null && civilVoice.getLatitude() != null) {
				civilVoice.setLocation("POINT(" + civilVoice.getLongitude() + " " + civilVoice.getLatitude() + ")");
			}
			civilVoiceService.insertCivilVoice(civilVoice);
		} catch(DataAccessException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "db.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ db.exception. message = {}", message);
		} catch(RuntimeException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "runtime.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ runtime.exception. message = {}", message);
		} catch(Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "unknown.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ exception. message = {}", message);
		}

		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}

	/**
	 * 시민참여 수정
	 * @param request
	 * @param civilVoice
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/{civilVoiceId}")
	public Map<String, Object> update(HttpServletRequest request, @PathVariable Long civilVoiceId, @Valid CivilVoice civilVoice, BindingResult bindingResult) {
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		log.info("@@ civilVoice = {}", civilVoice);

		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;

		try {
			if(civilVoiceId == null) {
				result.put("statusCode", HttpStatus.BAD_REQUEST.value());
				result.put("errorCode", "input.invalid");
				result.put("message", message);

				return result;
			}

			civilVoice.setUserId(userSession.getUserId());
			civilVoice.setCivilVoiceId(civilVoiceId);
			if(civilVoice.getLongitude() != null && civilVoice.getLatitude() != null) {
				civilVoice.setLocation("POINT(" + civilVoice.getLongitude() + " " + civilVoice.getLatitude() + ")");
			}
			civilVoiceService.updateCivilVoice(civilVoice);
		} catch(DataAccessException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "db.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ db.exception. message = {}", message);
		} catch(RuntimeException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "runtime.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ runtime.exception. message = {}", message);
		} catch(Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "unknown.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			log.info("@@ exception. message = {}", message);
		}

		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}

}
