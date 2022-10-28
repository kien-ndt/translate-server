package translate.app.api.translation.external.fetcher;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import translate.app.api.translation.external.entity.TranslationResult;
import translate.app.exception.ValidationErrorException;
import translate.app.exception.ValidationErrorException.ValidationError;

@Component
public class TranslationGetFetcher {
	
	private WebClient client = WebClient.create();
	
	@Value("${oxford.app_id}")
	private String appId;
	
	@Value("${oxford.app_key}")	
	private String appKey;
	
	private static final String API_URL = 
			"https://od-api.oxforddictionaries.com/api/v2/translations/{source_lang_translate}/{target_lang_translate}/{word}?strictMatch=false";
	
	public Mono<TranslationResult> fetchTranslationResult(String sourceLang, String targetLang, String word) {
		return client.get()
		.uri(UriComponentsBuilder.fromUriString(API_URL)
				.encode()
				.buildAndExpand(sourceLang, targetLang, word)
				.toUriString())
		.header("Accept", "application/json")
		.header("app_id", appId)
		.header("app_key", appKey)
		.retrieve()
		.onStatus(HttpStatus.URI_TOO_LONG::equals, 
				r -> Mono.error(
						new ValidationErrorException(
								"wrong params", "", Arrays.asList(new ValidationError("word", Arrays.asList("Too long"))))))
		.bodyToMono(TranslationResult.class)
		.onErrorResume(r -> {
			if (r instanceof ValidationErrorException) {
				return Mono.error(r);
			}
			else {
				TranslationResult result = new TranslationResult();
				result.id = word;
				return Mono.just(result);
			}
		});
	}

}
