package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 「飲み物の在庫」管理装置
 * @author kazuhito＿m
 */
public class DrinkStockManagementUnit {

	/** 飲み物のスロット群。 */
	private List<DrinkSlot> slots = new ArrayList<DrinkSlot>();

	/** コンストラクタ。 */
	public DrinkStockManagementUnit() {
		// 現仕様では、決め打ちで１スロット、中身も限定。
		DrinkSlot slot = new DrinkSlot();
		slot.setKind(DrinkKind.COLA);
		slot.setPrice(120);
		for (int i = 0; i < 5; i++) {
			slot.add(new Drink(slot.getKind()));
		}
		slots.add(slot);
	}

	/**
	 * スロット群を取得。<br>
	 * 「内部状態」であるスロットを取得するのは、<br>
	 * メンテナンスできるオブジェクトだけにしたいので、<br>
	 * パッケージ同一のクラスのみアクセス可能に。
	 * @return スロットのリスト。
	 */
	protected List<DrinkSlot> getSlots() {
		return slots;
	}

	public boolean existStock(DrinkKind cola) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
