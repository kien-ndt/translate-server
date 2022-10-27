package translate.app.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import translate.app.exception.ValidationErrorException;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Throwable exception = getError(request);
		
		if (exception instanceof ValidationErrorException) {
			return getValidationErrorExceptionAttributes((ValidationErrorException) exception);
		}
		
		if (exception instanceof Exception) {
		}
		
		Map<String, Object> errorAttributes = new HashMap<>();
		errorAttributes.put("gi do", "1231321");
		return errorAttributes;
	}
	
	private Map<String, Object> getValidationErrorExceptionAttributes(ValidationErrorException e) {
		Map<String, Object> errorAttributes = new HashMap<>();
		errorAttributes.put("httpStatus", e.getHttpStatus());
		errorAttributes.put("message", e.getMessage());
		errorAttributes.put("title", e.getTitle());
		errorAttributes.put("validationErrors", e.getValidationErrors());
		return errorAttributes;
	}
	
}
