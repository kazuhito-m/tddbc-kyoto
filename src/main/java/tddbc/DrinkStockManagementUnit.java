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

	/**
	 * 指定した飲み物種類で在庫があるかを検査。
	 * @param kind 飲み物種類。
	 * @return 判定結果。在庫あり:true
	 */
	public boolean existStock(DrinkKind kind) {
		for (DrinkSlot slot : slots) {
			if (slot.getKind() == kind) {
				if (slot.getStockCount() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 指定した飲み物種類の在庫を一つ取り出す。<br>
	 * 一度実効されるたび、その分の在庫が先入れ先出し法にて取り除かれます。
	 * @param kind 飲み物種類。
	 * @return 取り出した飲み物。在庫が無い場合:null。
	 */
	public Drink takeOut(DrinkKind kind) {
		for (DrinkSlot slot : slots) {
			if (slot.getKind() == kind) {
				return slot.takeOut();
			}
		}
		return null;
	}

	public int getPrice(DrinkKind cola) {
		// TODO 仮実装
		return 120;
	}
}
