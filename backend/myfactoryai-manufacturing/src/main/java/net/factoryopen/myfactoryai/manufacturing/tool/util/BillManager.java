package net.factoryopen.myfactoryai.manufacturing.tool.util;


import net.factoryopen.myfactoryai.manufacturing.tool.domain.BillIdName;
import net.factoryopen.myfactoryai.manufacturing.tool.domain.BillManagerEntity;
import net.factoryopen.myfactoryai.manufacturing.tool.mapper.BillManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class BillManager {

    @Autowired
    private BillManagerMapper billManagerMapper;
    private Integer lastDate = 0;
    private Integer nowDate = 0;
    private Integer nextNumber = 1;
    private String formatdate;
    private BillManagerEntity billManagerEntity;

    public void baseBill(long billId) {
//        根据billCode查询mdtBillcodeRule
        billManagerEntity = billManagerMapper.selectBillManagerById(billId);
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
    public String getNextBillno(long billId) {
        baseBill(billId);
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

    public void updateBillSeq(long billId) {
        baseBill(billId);
//        如果是第二天，后缀从1开始，nextNumber=1
        if (nowDate > lastDate) {
            nextNumber = 2;
        } else {
            nextNumber++;
        }
        billManagerEntity.setNextNumber(nextNumber);
        billManagerEntity.setLastDate(formatdate);
        billManagerMapper.updateBillManagerById(billManagerEntity);
    }

    public List<BillIdName> getBillIdNameList() {
        List<BillIdName> billIdNameList = billManagerMapper.getBillIdNameList();
        return billIdNameList;
    }

}
