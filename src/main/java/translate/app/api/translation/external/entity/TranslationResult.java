package translate.app.api.translation.external.entity;

import java.util.List;

public class TranslationResult {
	public String id;
	public List<BilingualHeadwordEntry> results;
	
	public static class BilingualHeadwordEntry {
		public String id;
		public String language;
		public List<BilingualLexicalEntry> lexicalEntries; 
		public String word;
		
		public static class BilingualLexicalEntry {
			public String language;
			public List<BilingualEntry> entries;
			public LexicalCategory lexicalCategory;
			
			public static class BilingualEntry {
				public List<Pronunciations> pronunciations;
				public List<BilingualSense> senses;
				
				public static class Pronunciations {
					public String audioFile;
					public List<String> dialects;
					public String phoneticSpelling;
				}
				
				public static class BilingualSense {
					public List<Examples> examples;
					public List<Translations> translations;
					
					public static class Examples {
						public String text;
						public List<Translations> translations;
					}
					
					public static class Translations {
						public String text;
					}
				}
			}
			
			public static class LexicalCategory {
				public String text;
			}
		}
	}
	
}
