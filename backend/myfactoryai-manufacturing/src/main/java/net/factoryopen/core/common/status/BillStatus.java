package net.factoryopen.core.common.status;

public class BillStatus {
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

    //已收
    public static final int ACTIVE_STARTED = 5;

    //请处理
    public static final int ACTIVE_PROCESS_REQ = 6;

    //计划中
    public static final int ACTIVE_PLANNING = 7;

    //处理中
    public static final int ACTIVE_PROCESSING = 8;

    //处理完
    public static final int ACTIVE_PROCESSED = 9;

    //有异常
    public static final int ACTIVE_EXCEPTIONAL = 10;

    //出入库相关动作
    public static final int ACTIVE_STOCK_ACTION = 11;

    //完成
    public static final int ACTIVE_TERMINATED = 12;

    //冻结
    public static final int LIFE_FROZEN = 13;

    //停用
    public static final int LIFE_DEAD = 14;

    //异常
    public static final int LIFE_EXCEPTIONAL = 15;

    //强制结案
    public static final int FINISH_WAITING = 16;

    //强制结案
    public static final int FINISH_FORCELY = 17;

    //结案
    public static final int FINISH = 18;

}
