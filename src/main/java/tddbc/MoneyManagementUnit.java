package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * お金(硬貨・紙幣)周り管理装置クラス。
 * @author kazuhito_m
 */
public class MoneyManagementUnit {

	/** 預かり金プール。 */
	private List<Money> depositPool = new ArrayList<Money>();

	/** つり銭ボックス。 */
	private List<Object> changeBox = new ArrayList<Object>();

	/**
	 * 総合計額を計算する。
	 */
	public int calcTotalAmount() {
		int total = 0;
		for (Money m : depositPool) {
			total += m.getAmount();
		}
		return total;
	}

	/**
	 * つり銭箱を返す。
	 * @return つり銭箱という名の紙幣・硬貨リスト。
	 */
	public List<Object> getChangeBox() {
		return changeBox;
	}

	/**
	 * (お金などの)投入を受ける。
	 * @param money 投入するモノ。
	 */
	public void receive(Object money) {
		if (money instanceof Money) {
			depositPool.add((Money) money);
		} else {
			changeBox.add(money);
		}
	}

	/**
	 * 返金する。
	 */
	public void refund() {
		changeBox.addAll(depositPool);
		depositPool.clear();
	}
}
