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
		
		slots.add(slot);
	}
	
	public List<DrinkSlot> getSlots() {
		return slots;
	} 
}
