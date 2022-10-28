package translate.app.external.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WordEntity {	
	@Id
	public String id;
	public String text;
	public String sourceLang;
	public Integer searchTime;	
}
