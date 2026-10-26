package net.factoryopen.myfactoryai.erp.support;

import java.time.LocalDate;
import java.util.UUID;

/**
 * 业务单据号工具。
 *
 * <p>各单据表的单号列统一是 {@code varchar(30)}，而正式单号形如 {@code PO2026000001}
 * 依赖自增主键，只能在 insert 之后回填。所以 insert 时必须先写一个占位值，
 * 拿到主键后再用 {@link #formal(String, long)} 覆盖。</p>
 *
 * <p>历史问题：占位值曾写成 {@code prefix + "-TMP-" + UUID}（7 + 36 = 43 字符），
 * 超出 varchar(30)，导致所有单据新建都抛
 * {@code Data too long for column 'xxx_no'}。统一走本类可避免再踩。</p>
 */
public final class DocNos {

    /** 占位值里随机段的字符数；2 + 5 + 20 = 27，留 3 字符余量。 */
    private static final int RANDOM_LEN = 20;

    private DocNos() {
    }

    /**
     * 生成 27 字符的临时单号，例如 {@code PO-TMP-3f2a1c9e8b7d6a5c4e3b}。
     *
     * @param prefix 两位单据前缀，如 PO / SO / QT / SH / RT / WO / CS
     */
    public static String temp(String prefix) {
        if (prefix == null || prefix.isEmpty() || prefix.length() > 4) {
            throw new IllegalArgumentException("单据前缀长度必须为 1 至 4：" + prefix);
        }
        return prefix + "-TMP-" + UUID.randomUUID().toString().replace("-", "").substring(0, RANDOM_LEN);
    }

    /**
     * 正式单号：两位前缀 + 四位年份 + 六位流水，例如 {@code PO2026000001}。
     *
     * @param prefix 两位单据前缀
     * @param id     自增主键
     */
    public static String formal(String prefix, long id) {
        return prefix + LocalDate.now().getYear() + String.format("%06d", id);
    }
}
