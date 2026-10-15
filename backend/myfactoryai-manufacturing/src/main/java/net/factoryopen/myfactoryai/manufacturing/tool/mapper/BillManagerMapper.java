package net.factoryopen.myfactoryai.manufacturing.tool.mapper;

import net.factoryopen.myfactoryai.manufacturing.tool.domain.BillIdName;
import net.factoryopen.myfactoryai.manufacturing.tool.domain.BillManagerEntity;

import java.util.List;

/**
 * 单据规则Mapper接口
 *
 * @author admin
 * @date 2022-07-29
 */
public interface BillManagerMapper {
    /**
     * 查询单据规则
     *
     * @param billId 单据规则主键
     * @return 单据规则
     */
    public BillManagerEntity selectBillManagerById(long billId);

    public int updateBillManagerById(BillManagerEntity billManagerEntity);

    /**
     * 获取订单code name
     *
     * @return
     */
    public List<BillIdName> getBillIdNameList();

}
