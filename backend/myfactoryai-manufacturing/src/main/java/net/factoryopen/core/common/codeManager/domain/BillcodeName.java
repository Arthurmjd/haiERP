package net.factoryopen.core.common.codeManager.domain;

public class BillcodeName {
    private long billId;
    private String billName;

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    @Override
    public String toString() {
        return "BillIdName{" +
                "billId=" + billId +
                ", billName='" + billName + '\'' +
                '}';
    }
}
