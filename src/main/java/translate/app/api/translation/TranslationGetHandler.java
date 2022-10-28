package translate.app.api.translation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import translate.app.api.translation.external.entity.TranslationResult;
import translate.app.api.translation.external.entity.TranslationResult.BilingualHeadwordEntry;
import translate.app.api.translation.external.fetcher.TranslationGetFetcher;
import translate.app.api.translation.mapping.MappingTranslationGetResponse;
import translate.app.api.translation.resource.TranslationGetRequestParams;
import translate.app.api.translation.resource.TranslationGetResponse;
import translate.app.api.translation.resource.TranslationGetResponse.Results;
import translate.app.api.translation.validation.TranslationGetValidator;

@Service
public class TranslationGetHandler {
	
	@Autowired
	private TranslationGetValidator validator;
	
	@Autowired
	private TranslationGetFetcher fetcher;
	
	@Autowired
	private MappingTranslationGetResponse mapping;
	
	
	public Mono<ServerResponse> getTranslation(ServerRequest serverRequest) {
						
		Mono<TranslationGetResponse> result = 
				Mono.just(new TranslationGetRequestParams(
						serverRequest.queryParam("source_lang_translate").orElse(""), 
						serverRequest.queryParam("target_lang_translate").orElse(""),
						serverRequest.queryParam("word").orElse("")))
				.flatMap(validator::validate)
				.flatMap(requestParams -> 
					fetcher.fetchTranslationResult(
							((TranslationGetRequestParams) requestParams).getSourceLang(),
							((TranslationGetRequestParams) requestParams).getTargetLang(),
							((TranslationGetRequestParams) requestParams).getWord()))
				.flatMap(translationResult -> Mono.just(mapping.mappingResponseBody(translationResult)))
				.onErrorResume(r -> Mono.error(r));
		return ServerResponse.ok().body(result, TranslationResult.class);
	}

}
