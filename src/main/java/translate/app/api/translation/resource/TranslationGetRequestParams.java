package translate.app.api.translation.resource;

public class TranslationGetRequestParams {
	private String sourceLang;
	private String targetLang;
	
	public TranslationGetRequestParams(String sourceLang, String targetLang) {
		setSourceLang(sourceLang);
		setTargetLang(targetLang);
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
	
	
}
