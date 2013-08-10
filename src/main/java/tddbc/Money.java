package tddbc;

/**
 * Created with IntelliJ IDEA.
 * User: kyon_mm
 * Date: 2013/08/10
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public enum Money {
    _10 {
        @Override
        int value() {
            return 10;
        }
    }, _50 {
        @Override
        int value() {
            return 50;
        }
    }, _100 {
        @Override
        int value() {
            return 100;
        }
    }, _500 {
        @Override
        int value() {
            return 500;
        }
    }, _1000 {
        @Override
        int value() {
            return 1000;
        }
    };
    abstract int value();

}
