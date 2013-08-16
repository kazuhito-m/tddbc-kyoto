package tddbc;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

	// プロパティ群
	
	/** お金(硬貨・紙幣)周り管理装置。 */
	private MoneyManagementUnit moneyManager;

	/** 「飲み物の在庫」管理装置。 */
	private DrinkStockManagementUnit drinkStockManager =  new DrinkStockManagementUnit(); 

	/** 購入後飲み物取り出し口 */
	private List<Drink> outTray = new ArrayList<Drink>();

	// メソッド群
	
	/** コンストラクタ。 */
	public VendingMachine() {
		moneyManager = new MoneyManagementUnit();
	}

	/**
	 * 総合計額を表示する。
	 * @return
	 */
	public int displayTotalAmount() {
		return moneyManager.calcTotalAmount();
	}

	/**
	 * つり銭箱を返す。
	 * 
	 * @return
	 */
	public List<Object> getChangeBox() {
		return moneyManager.getChangeBox();
	}

	/**
	 * (お金などの)投入を受ける。
	 * 
	 * @param money
	 */
	public void receive(Object money) {
		moneyManager.receive(money);
	}

	/**
	 * 返金する。
	 */
	public void refund() {
		moneyManager.refund();
	}

	/**
	 * 現在の状態で指定された飲み物種を販売可能かを検査する。
	 * @param kind 購入対象の飲み物種。
	 * @return 検査結果。可能:true。
	 */
	public boolean isSellable(DrinkKind kind) {
		// 在庫管理装置から、指定された飲み物種の価格を取得する。
		int price = drinkStockManager.getPrice(kind);
		// 管理外なら、無論販売不可能。
		if (price  == -1) {
			return false;
		}
		// 現在の投入金額はそれを買うに十分なお金か否か。
		if (moneyManager.calcTotalAmount() < price) {
			return false;
		}
		// 最後まで来た→販売可能。true返す。
		return true;
	}
}
