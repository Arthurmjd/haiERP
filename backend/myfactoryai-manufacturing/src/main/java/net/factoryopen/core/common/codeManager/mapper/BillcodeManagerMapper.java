package net.factoryopen.core.common.codeManager.mapper;

import net.factoryopen.core.common.codeManager.domain.BillcodeName;
import net.factoryopen.core.common.codeManager.domain.BillcodeManagerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 单据规则Mapper接口
 *
 * @author admin
 * @date 2022-07-29
 */
@Mapper
public interface BillcodeManagerMapper {
    /**
     * 查询单据规则
     *
     * @param billId 单据规则主键
     * @return 单据规则
     */
    public BillcodeManagerEntity selectBillcodeManagerById(long billId);

    public BillcodeManagerEntity selectBillcodeManagerByCode(String billCode);

    public int updateBillcodeManagerById(BillcodeManagerEntity billcodeManagerEntity);

    /**
     * 获取订单code name
     *
     * @return
     */
    public List<BillcodeName> getBillcodeNameList();

}
