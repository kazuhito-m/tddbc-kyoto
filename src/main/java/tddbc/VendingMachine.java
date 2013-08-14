package tddbc;

import java.util.List;

public class VendingMachine {

	// プロパティ群
	
	/** お金(硬貨・紙幣)周り管理装置。 */
	private MoneyManagementUnit moneyManager;

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
}
