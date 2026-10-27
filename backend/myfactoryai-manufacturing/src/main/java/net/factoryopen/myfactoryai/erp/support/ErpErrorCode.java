package net.factoryopen.myfactoryai.erp.support;

/**
 * ERP 业务错误码。
 *
 * <p>集中管理服务层抛出的 {@link com.ruoyi.common.exception.ServiceException} 码值，
 * 码值本身是前后端契约的一部分（前端按码处理特定分支），禁止改动数值，只允许新增。</p>
 */
public enum ErpErrorCode {

    /** 参数校验失败：必填缺失、格式不合法、目标记录不存在 */
    INVALID_PARAM(1001, "参数校验失败"),

    /** 数据重复：编码/名称/明细行已存在 */
    DUPLICATE(1002, "数据重复"),

    /** 状态不允许：单据状态已变化或当前状态不支持该操作 */
    INVALID_STATE(2001, "状态不允许"),

    /** 余额或库存不足：资金账户余额、应收应付余额、成品/物料库存 */
    INSUFFICIENT(2003, "余额或库存不足"),

    /** 试算前提不满足：BOM/工序/取价/数量等试算输入缺失或不合法 */
    CALC_PRECONDITION(2005, "试算前提不满足"),

    /** 并发冲突：单据已被他人修改，需刷新后重试 */
    CONFLICT(2006, "数据已变化"),

    /** 幂等冲突：请求键已用于不同操作，或请求记录与目标单据不一致 */
    IDEMPOTENCY_CONFLICT(2007, "请求记录不一致"),

    /** 没有该业务数据权限 */
    DATA_SCOPE_DENIED(4002, "没有该业务数据权限");

    private final int code;
    private final String label;

    ErpErrorCode(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int code() {
        return code;
    }

    public String label() {
        return label;
    }
}