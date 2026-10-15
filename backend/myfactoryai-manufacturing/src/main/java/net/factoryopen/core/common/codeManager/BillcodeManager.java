package net.factoryopen.core.common.codeManager;


import net.factoryopen.core.common.codeManager.domain.BillcodeName;
import net.factoryopen.core.common.codeManager.domain.BillcodeManagerEntity;
import net.factoryopen.core.common.codeManager.mapper.BillcodeManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class BillcodeManager {

    @Autowired
    private BillcodeManagerMapper billcodeManagerMapper;
    private Integer lastDate = 0;
    private Integer nowDate = 0;
    private Integer nextNumber = 1;
    private String formatdate;
    private BillcodeManagerEntity billManagerEntity;

    public void baseBill(String billCode) {
//        根据billCode查询mdtBillcodeRule
        billManagerEntity = billcodeManagerMapper.selectBillcodeManagerByCode(billCode);
//        获取日期格式
        String periodPattern = billManagerEntity.getPeriodPattern();
//        根据时间格式获取系统时间，转成String
        SimpleDateFormat formatter = new SimpleDateFormat(periodPattern);
        formatdate = formatter.format(new Date());
//        获取当前数据库最新时间，转成String
        String oldDate = billManagerEntity.getLastDate();
        String[] dates = oldDate.split(" ")[0].split("-");
        StringBuilder date = new StringBuilder();
        for (String dd : dates) {
            date.append(dd);
        }
//        把时间转成int
        lastDate = Integer.parseInt(String.valueOf(date));
        nowDate = Integer.parseInt(formatdate);

    }

    //    编码
    public String getNextBillno(String billCode) {
        baseBill(billCode);
//        获取下一个num
        nextNumber = billManagerEntity.getNextNumber();
//        第二天，如果是第二天，后缀从1开始，nextNumber=1
        if (nowDate > lastDate) {
            nextNumber = 1;
        }
//    生成单号
        String billNumber;
//        订单后缀
        String str = String.format("%0" + billManagerEntity.getNumberSize() + "d", nextNumber);
//        判断是否启用了表单前缀
        if ("0".equals(billManagerEntity.getDisableCode())) {

            billNumber = billManagerEntity.getBillCode() + nowDate + str;
        } else {
//            若没有启用订单前缀，则添加默认JIT
            billNumber = "JIT" + nowDate + str;
        }
        return billNumber;
    }

    public void updateBillSeq(String billCode) {
        baseBill(billCode);
//        如果是第二天，后缀从1开始，nextNumber=1
        if (nowDate > lastDate) {
            nextNumber = 2;
        } else {
            nextNumber++;
        }
        billManagerEntity.setNextNumber(nextNumber);
        billManagerEntity.setLastDate(formatdate);
        billcodeManagerMapper.updateBillcodeManagerById(billManagerEntity);
    }

    public List<BillcodeName> getBillIdNameList() {
        List<BillcodeName> billcodeNameList = billcodeManagerMapper.getBillcodeNameList();
        return billcodeNameList;
    }

}
