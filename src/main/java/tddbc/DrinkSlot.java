package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 「飲み物の内部スロット」クラス。
 * @author kazuhito_m
 */
public class DrinkSlot {

	/** 飲み物の種類。 */
	private DrinkKind kind;

	/** 販売価格。 */
	private int price;

	/** 飲み物のため場所。 */
	private List<Drink> drinks = new ArrayList<Drink>();
	
	public DrinkKind getKind() {
		return kind;
	}

	/** 
	 * 補充。
	 * @param drink 飲み物。
	 */
	public void add(Drink drink) {
		drink.setKind(kind);
		drinks.add(drink);
	}

	/**
	 * スロットの在庫個数。
	 * @return 個数。
	 */
	public int getStockCount() {
		return drinks.size();
	}

	// プロパティ群
	
	public void setKind(DrinkKind kind) {
		this.kind = kind;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 飲み物を一個取り出す。<br>
	 * (外へガシャコン！と落とすイメージ。)
	 * @return 飲み物一個。
	 */
	public Drink takeOut() {
		return new Drink(null);
	}

}
