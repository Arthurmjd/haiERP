package net.factoryopen.core.common.status;

public class BillFullStatus2 {
    //底稿
    public static final int BASE_DRAFT = 0;

    //请审
    public static final int BASE_AUDIT_REQUEST = 1;

    //拒审
    public static final int BASE_AUDIT_REJECT = 2;

    //过审
    public static final int BASE_AUDIT_PASS = 3;

    //启用
    public static final int BASE_ENABLE = 4;

    //已收接
    public static final int ACTIVE_ACCEPTED = 10;

    //请处理
    public static final int ACTIVE_REQUEST = 11;

    //筹处理
    public static final int ACTIVE_PLANNING = 12;

    //在处理
    public static final int ACTIVE_PROCESSING = 13;

    //在处理
    public static final int ACTIVE_DONE_WAITING = 14;

    //将处理完
    public static final int ACTIVE_DONE = 15;

    //请收货
    public static final int PSI_STOCKIN_REQUEST = 20;

    //已收货
    public static final int PSI_STOCKIN_RECEIVED = 21;

    //请检中
    public static final int PSI_QC_REQUEST = 22;

    //已收检
    public static final int PSI_QC_RECEIVED = 23;

    //已检验
    public static final int PSI_QC_DONE = 24;

    //已检验
    public static final int PSI_QC_JUDGED = 25;

    //已检验
    public static final int PSI_QC_DEALED = 26;

    //待入库
    public static final int PSI_STOCKIN_WAITING = 27;

    //已入库
    public static final int PSI_STOCKIN_DONE = 28;

    //冻结
    public static final int LIFE_FROZEN = 30;

    //停用
    public static final int LIFE_DEAD = 31;

    //异常
    public static final int LIFE_EXCEPTIONAL = 32;

    //强制结案
    public static final int FINISH_WAITING = 40;

    //强制结案
    public static final int FINISH_FORCELY = 41;

    //结案
    public static final int FINISH = 42;

}
