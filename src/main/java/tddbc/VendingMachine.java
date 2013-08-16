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

	public boolean isSellable(DrinkKind cola) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
