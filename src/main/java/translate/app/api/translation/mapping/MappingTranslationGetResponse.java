package translate.app.api.translation.mapping;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import translate.app.api.translation.external.entity.TranslationResult;
import translate.app.api.translation.external.entity.TranslationResult.BilingualHeadwordEntry;
import translate.app.api.translation.resource.TranslationGetResponse;
import translate.app.api.translation.resource.TranslationGetResponse.Results;

@Service
public class MappingTranslationGetResponse {
	
	public TranslationGetResponse mappingResponseBody(TranslationResult resultFetcher) {
		TranslationGetResponse response = new TranslationGetResponse();
		response.id = resultFetcher.id;
		if (resultFetcher.results != null && resultFetcher.results.size() > 0) {			
			BilingualHeadwordEntry targetResult = resultFetcher.results.get(0);			
			if (targetResult.lexicalEntries != null) {
				response.results = new ArrayList<Results>();
				targetResult.lexicalEntries.forEach(lexicalEntry -> {
					Results resResult = new Results();
					resResult.category = lexicalEntry.lexicalCategory != null ? lexicalEntry.lexicalCategory.text : null;
					resResult.language = lexicalEntry.language;
					resResult.entries = lexicalEntry.entries;										
					response.results.add(resResult);
				});
			}		
			
		}
		
		return response;
	}
}
