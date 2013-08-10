package tddbc;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private List<Integer> changeBox;

    /**
	 * 総合計額を返す。
	 * @return
	 */
	public int displayTotalAmount() {
		return 0;
	}

	/**
	 * つり銭箱を返す。
	 * @return
	 */
	public List<Integer> getChangeBox() {
		changeBox = new ArrayList<Integer>();
		return changeBox;
	}

    public void receive(Object money){


    }
}
