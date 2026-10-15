package net.factoryopen.core.common.type;

public class InventoryTransactionType {
    //转包入库
    public static final int STOCKIN_4_SUBCONTRACT = 1;

    //采购入库
    public static final int STOCKIN_4_BUY = 2;

    //外协入库
    public static final int STOCKIN_OUTER_SUPPORT = 6;

    //生产入库
    public static final int STOCKIN_PRODUCTING = 7;

    //生产退料
    public static final int STOCKIN_4_PICKBACK = 5;

    //销售退货
    public static final int STOCKIN_4_SALE_BACK = 9;

    //其他入库
    public static final int STOCKIN_4_OTHERS = 18;

    //销售发货
    public static final int STOCKOUT_4_SALE = 8;

    //生产领料
    public static final int STOCKOUT_4_PICK = 4;

    //采购退货
    public static final int STOCKOUT_4_BUYBACK = 3;

    //其他出库
    public static final int STOCKOUT_4_OTHERS = 19;

    //存货报废
    public static final int MATERIAL_SCRAP = 20;

    //存货报废
    public static final int MATERIAL_PENDING = 21;

    //存货过期
    public static final int MATERIAL_EXPIRED = 22;

    //存货冻结
    public static final int MATERIAL_FROZEN = 23;

    //存货移位
    public static final int MATERIAL_MOVE_POSITION = 31;

    //跨库调拨
    public static final int MATERIAL_SHIFT_WAREHOUSE = 32;

}
