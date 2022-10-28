package translate.app.api.translation.resource;

public class TranslationGetRequestParams {
	private String sourceLang;
	private String targetLang;
	private String word;
	
	public TranslationGetRequestParams(String sourceLang, String targetLang, String word) {
		setSourceLang(sourceLang);
		setTargetLang(targetLang);
		setWord(word);
	}
	
	public String getSourceLang() {
		return sourceLang;
	}
	public void setSourceLang(String sourceLang) {
		this.sourceLang = sourceLang;
	}
	public String getTargetLang() {
		return targetLang;
	}
	public void setTargetLang(String targetLang) {
		this.targetLang = targetLang;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	
}
