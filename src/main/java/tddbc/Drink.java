package tddbc;

/**
 * 「飲み物」クラス。<br>
 * インスタンスは、現実世界においてのいわゆる「缶ジュース一つ」に相当する。
 * @author kazuhito_m
 */
public class Drink {

	/** ジュースの種類 */
	private DrinkKind kind;

	/** 時価(購入時の価格) */
	private int amountOfTime;

	/** コンストラクタ */
	public Drink(DrinkKind kind) {
		this.kind = kind;
	}

	/**
	 * 「ジュースの名前」を取得する。
	 * @return 名前文字列。
	 */
	public String getCaption() {
		return kind.getCaption();
	}

	/**
	 * 時価(購入時の価格)。
	 * @return 価格数値。
	 */
	public int getAmountOfTime() {
		return this.amountOfTime;
	}

	/**
	 * 時価の変更。
	 * @param amountOfTime 新しい金額。
	 */
	protected void setAmountOfTime(int amountOfTime) {
		this.amountOfTime = amountOfTime;
	}

	/** 
	 * 「ジュースの種類」の変更。
	 * @param kind 新しい種類。
	 */
	protected void setKind(DrinkKind kind) {
		this.kind = kind;
	}


}
