package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 「飲み物の内部スロット」クラス。
 * @author kazuhito_m
 */
public class DrinkSlot {

    /** 飲み物の種類。 */
    private DrinkKind kind;

    /** 販売価格。 */
    private int price;

    /** 飲み物のため場所。 */
    private final List<Drink> drinks = new ArrayList<Drink>();

    public DrinkKind getKind() {
        return kind;
    }

    /**
     * 補充。
     * @param drink 飲み物。
     */
    public void add(final Drink drink) {
        drink.setKind(kind);
        drinks.add(drink);
    }

    /**
     * スロットの在庫個数。
     * @return 個数。
     */
    public int getStockCount() {
        return drinks.size();
    }

    // プロパティ群

    public void setKind(final DrinkKind kind) {
        this.kind = kind;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * 飲み物を一個取り出す。<br>
     * (外へガシャコン！と落とすイメージ)<br>
     * 在庫管理法は「後入れ先出し」とする。
     * @return 飲み物一個。
     */
    public Drink takeOut() {
        if (this.drinks.size() == 0) {
            return null;
        } else {
            Drink outDrink = drinks.remove(0);
            outDrink.setAmountOfTime(this.price); // 販売時に時価は決まる。
            return outDrink;
        }
    }
}