package translate.app.api.translation.resource;

import java.util.List;

import translate.app.api.translation.external.entity.TranslationResult.BilingualHeadwordEntry.BilingualLexicalEntry.BilingualEntry;

public class TranslationGetResponse {
	public String id;
	public List<Results> results;
	
	public static class Results {
		public String language;
		public List<BilingualEntry> entries;
		public String category;
	}
}
