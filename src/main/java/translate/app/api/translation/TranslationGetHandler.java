package translate.app.api.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import translate.app.api.translation.external.entity.TranslationResult;
import translate.app.api.translation.external.fetcher.TranslationGetFetcher;
import translate.app.api.translation.resource.TranslationGetRequestParams;
import translate.app.api.translation.validation.TranslationGetValidator;

@Service
public class TranslationGetHandler {
	
	@Autowired
	private TranslationGetValidator validator;
	
	@Autowired
	private TranslationGetFetcher fetcher;
	
	
	public Mono<ServerResponse> getTranslation(ServerRequest serverRequest) {
						
		Mono<TranslationResult> result = 
				Mono.just(new TranslationGetRequestParams(
						serverRequest.queryParam("source_lang_translate").orElse(""), 
						serverRequest.queryParam("target_lang_translate").orElse("")))
				.flatMap(validator::validate)
				.flatMap(requestParams -> 
					fetcher.fetchTranslationResult(
							((TranslationGetRequestParams) requestParams).getSourceLang(),
							((TranslationGetRequestParams) requestParams).getTargetLang()))
				.onErrorResume(r -> Mono.error(r));
		return ServerResponse.ok().body(result, TranslationResult.class);
	}

}
