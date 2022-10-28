package translate.app.api.translation.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import reactor.core.publisher.Mono;
import translate.app.api.translation.resource.TranslationGetRequestParams;
import translate.app.exception.ValidationErrorException;
import translate.app.exception.ValidationErrorException.ValidationError;

@Service
public class TranslationGetValidator {

	public Mono<TranslationGetRequestParams> validate(TranslationGetRequestParams target) {
		List<ValidationError> validationError = new ArrayList<>();
		String sourceLang = target.getSourceLang();
		String targetLang = target.getTargetLang();
		String word = target.getWord();
		if (!StringUtils.hasText(sourceLang)) {
			validationError.add(new ValidationError("sourceLang", Arrays.asList("Not empty")));
		}
		else {
			if (sourceLang.length() > 2) {
				validationError.add(new ValidationError("sourceLang", Arrays.asList("Too long")));				
			}
		}
		
		if (!StringUtils.hasText(targetLang)) {
			validationError.add(new ValidationError("targetLang", Arrays.asList("Not empty")));
		}
		else {
			if (targetLang.length() > 2) {
				validationError.add(new ValidationError("targetLang", Arrays.asList("Too long")));				
			}
		}
		
		if (!StringUtils.hasText(word)) {
			validationError.add(new ValidationError("word", Arrays.asList("Not empty")));
		}
		
		if (!CollectionUtils.isEmpty(validationError)) {
			return Mono.error(new ValidationErrorException("wrong params", "", validationError));
		}
		return Mono.just(target);		
	}

}
