package tddbc;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private List<Object> changeBox = new ArrayList<Object>();

    private List<Money> amountBox = new ArrayList<Money>();

    /**
	 * 総合計額を返す。
	 * @return
	 */
	public int displayTotalAmount() {
        int total = 0;
        for (Money m : amountBox) {
             total += m.value();
        }
       return total;
	}

	/**
	 * つり銭箱を返す。
	 * @return
	 */
	public List<Object> getChangeBox() {
		return changeBox;
	}

    public void receive(Object money){
        if(money instanceof Money){
           this.amountBox.add((Money) money);
        }
        else{
            changeBox.add(money);
        }
    }
}
