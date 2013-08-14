package tddbc;

/**
 * 「飲み物の内部スロット」クラス。
 * @author kazuhito_m
 */
public class DrinkSlot {

	/** 飲み物の種類。 */
	private DrinkKind kind;

	/** 販売価格。 */
	private int price;

	public DrinkKind getKind() {
		return kind;
	}

	public void setKind(DrinkKind kind) {
		this.kind = kind;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
