package top.jplayer.baseprolibrary.mvp.model.bean;

/**
 * Created by Obl on 2018/4/1.
 * top.jplayer.baseprolibrary.mvp.model.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class MoneyBean {

    /**
     * errorCode : 0000
     * data : {"userName":"17085329627","totalMoney":8308,"total":86}
     */

    public String errorCode;
    public DataBean data;

    public static class DataBean {
        /**
         * userName : 17085329627
         * totalMoney : 8308
         * total : 86
         */

        public String userName;
        public int totalMoney;
        public int total;
    }
}
