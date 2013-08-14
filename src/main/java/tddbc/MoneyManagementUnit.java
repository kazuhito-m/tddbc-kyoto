package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * お金(硬貨・紙幣)周り管理装置クラス。
 * @author kazuhito_m
 */
public class MoneyManagementUnit {

	private List<Object> changeBox = new ArrayList<Object>();

	private List<Money> amountBox = new ArrayList<Money>();

	/**
	 * 総合計額を表示する。
	 * 
	 * @return
	 */
	public int displayTotalAmount() {
		int total = 0;
		for (Money m : amountBox) {
			total += m.getAmount();
		}
		return total;
	}

	/**
	 * つり銭箱を返す。
	 * 
	 * @return
	 */
	public List<Object> getChangeBox() {
		return changeBox;
	}

	/**
	 * (お金などの)投入を受ける。
	 * 
	 * @param money
	 */
	public void receive(Object money) {
		if (money instanceof Money) {
			this.amountBox.add((Money) money);
		} else {
			changeBox.add(money);
		}
	}

	/**
	 * 返金する。
	 */
	public void refund() {
		changeBox.addAll(amountBox);
		amountBox.clear();
	}
}
