package tddbc;

/**
 * 「飲み物の種類」定数群。
 * @author kazuhito_m
 */
public enum DrinkKind {
	COLA("コーラ");

	/** 内部「飲み物の名前」 */
	private final String caption;
	
	/** コンストラクタ。 */
	DrinkKind(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
}
