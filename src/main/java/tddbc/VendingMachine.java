package tddbc;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    // プロパティ群

    /** お金(硬貨・紙幣)周り管理装置。 */
    private final MoneyManagementUnit moneyManager;

    /** 「飲み物の在庫」管理装置。 */
    private final DrinkStockManagementUnit drinkStockManager = new DrinkStockManagementUnit();

    /** 購入後飲み物取り出し口 */
    private final List<Drink> outTray = new ArrayList<Drink>();

    // メソッド群

    /** コンストラクタ。 */
    public VendingMachine() {
        moneyManager = new MoneyManagementUnit();
    }

    /**
     * 総合計額を表示する。
     * @return 合計金額数値。
     */
    public final int displayTotalAmount() {
        return moneyManager.calcTotalAmount();
    }

    /**
     * つり銭箱を返す。
     * @return つり銭箱オブジェクト。
     */
    public final List<Object> getChangeBox() {
        return moneyManager.getChangeBox();
    }

    /**
     * 飲み物の取り出し愚痴を返す。
     * @return 取り出し口の箱イメージ。
     */
    public final List<Drink> getOutTray() {
        return this.outTray;
    }

    /**
     * (お金などの)投入を受ける。
     * @param money 投入するお金。
     */
    public final void receive(final Object money) {
        moneyManager.receive(money);
    }

    /**
     * 返金する。
     */
    public final void refund() {
        moneyManager.refund();
    }

    /**
     * 現在の状態で指定された飲み物種を販売可能かを検査する。
     * @param kind 購入対象の飲み物種。
     * @return 検査結果。可能:true。
     */
    public final boolean isSellable(final DrinkKind kind) {
        // 在庫管理装置から、指定された飲み物種の価格を取得する。
        int price = drinkStockManager.getPrice(kind);
        // 管理外なら、無論販売不可能。
        if (price == -1) {
            return false;
        }
        // 現在の投入金額はそれを買うに十分なお金か否か。
        if (moneyManager.calcTotalAmount() < price) {
            return false;
        }
        // 在庫はあるか。
        if (!drinkStockManager.existStock(kind)) {
            return false;
        }
        // 最後まで来た→販売可能。true返す。
        return true;
    }

    /**
     * 飲み物を購入する。(自販機が売る)<br/>
     * @param kind 対象の飲み物種。
     * @return 成功判定。成功:true。
     */
    public final boolean sale(final DrinkKind kind) {
        // 最初に「購入できるか否か」は自分でもチェック
        if (!isSellable(kind)) {
            return false;
        }
        // 在庫から一つ取り出す。
        Drink saledDrink = drinkStockManager.takeOut(kind);
        // 買ったお金を売上処理する。(飲み物は自身の販売価格を知っている)
        moneyManager.withdrawToIncome(saledDrink.getAmountOfTime());
        // 商品トレイに購入処理した飲み物を流す。
        outTray.add(saledDrink);
        // 成功を返す。
        return true;
    }

    /**
     * この自販機での「購入可能な飲み物」を一覧表示する。
     * @return 買うことのできる飲み物のリスト。
     */
    public final List<DrinkKind> displaySellableDrinks() {
        return drinkStockManager.getValiedDrinks();
    }

    /**
     * この自販機で「現在の状態(投入金額、在庫など)」において「購入可能な飲み物」を一覧表示する。
     * @return 買うことのできる飲み物のリスト。
     */
    public final List<DrinkKind> displaySellableDrinksNowForYou() {
        List<DrinkKind> validDrinks = new ArrayList<DrinkKind>();
        // 買うことのできる全飲み物種を回す
        for (DrinkKind kind : displaySellableDrinks()) {
            // 個々「購入可能か」メソッドで結果判定
            if (isSellable(kind)) {
                validDrinks.add(kind);
            }
        }
        return validDrinks;
    }

    /**
     * 外部からの操作用「在庫管理装置」。<br/>
     * メンテナンス用の取得口。<br/>
     * そのため同一パッケージ内(例えばテスト側)からしかアクセス出来無い。
     * @return 在庫管理装置オブジェクト。
     */
    protected final DrinkStockManagementUnit getDrinkStockManager() {
        return this.drinkStockManager;
    }

    /**
     * 外部からの操作用「お金管理装置」。<br/>
     * メンテナンス用の取得口。<br/>
     * そのため同一パッケージ内(例えばテスト側)からしかアクセス出来無い。
     * @return お金管理装置オブジェクト。
     */
    protected final MoneyManagementUnit getMoneyManager() {
        return this.moneyManager;
    }

}
