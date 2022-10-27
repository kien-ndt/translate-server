package translate.app.api.translation.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import reactor.core.publisher.Mono;
import translate.app.api.translation.resource.TranslationGetRequestParams;
import translate.app.exception.ValidationErrorException;
import translate.app.exception.ValidationErrorException.ValidationError;

@Service
public class TranslationGetValidator {

	public Mono<Object> validate(TranslationGetRequestParams target) {
		List<ValidationError> validationError = new ArrayList<>();
		if (!StringUtils.hasText(target.getSourceLang())) {
			validationError.add(new ValidationError("sourceLang", Arrays.asList("Not empty")));
		}
		else {
			if (target.getSourceLang().length() > 2) {
				validationError.add(new ValidationError("sourceLang", Arrays.asList("Too long")));				
			}
		}
		
		if (!StringUtils.hasText(target.getTargetLang())) {
			validationError.add(new ValidationError("targetLang", Arrays.asList("Not empty")));
		}
		else {
			if (target.getTargetLang().length() > 2) {
				validationError.add(new ValidationError("targetLang", Arrays.asList("Too long")));				
			}
		}
		if (!CollectionUtils.isEmpty(validationError)) {
			return Mono.error(new ValidationErrorException("wrong params", "", validationError));
		}
		return Mono.just(target);		
	}

}
