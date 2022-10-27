package translate.app.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ValidationErrorException extends RuntimeException {
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private String title;
	private List<ValidationError> validationErrors;
	
	public ValidationErrorException(
			String message, String title, List<ValidationError> validationErrors) {
		super(message);
		this.title = title;
		this.validationErrors = validationErrors;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public static class ValidationError {
		private String name;
		private List<String> message;
		
		public ValidationError(String name, List<String> message) {
			this.name = name;
			this.message = message;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getMessage() {
			return message;
		}
		public void setMessage(List<String> message) {
			this.message = message;
		}
	}

}
