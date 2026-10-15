package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityDaytrend;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomQualityDaytrendMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityDaytrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 质异日势Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomQualityDaytrendServiceImpl implements IHomQualityDaytrendService {
    @Autowired
    private HomQualityDaytrendMapper homQualityDaytrendMapper;

    /**
     * 查询质异日势
     *
     * @param id 质异日势主键
     * @return 质异日势
     */
    @Override
    public HomQualityDaytrend selectHomQualityDaytrendById(Long id) {
        return homQualityDaytrendMapper.selectHomQualityDaytrendById(id);
    }

    /**
     * 查询质异日势列表
     *
     * @param homQualityDaytrend 质异日势
     * @return 质异日势
     */
    @Override
    public List<HomQualityDaytrend> selectHomQualityDaytrendList(HomQualityDaytrend homQualityDaytrend) {
        return homQualityDaytrendMapper.selectHomQualityDaytrendList(homQualityDaytrend);
    }

    /**
     * 新增质异日势
     *
     * @param homQualityDaytrend 质异日势
     * @return 结果
     */
    @Override
    public int insertHomQualityDaytrend(HomQualityDaytrend homQualityDaytrend) {
        return homQualityDaytrendMapper.insertHomQualityDaytrend(homQualityDaytrend);
    }

    /**
     * 修改质异日势
     *
     * @param homQualityDaytrend 质异日势
     * @return 结果
     */
    @Override
    public int updateHomQualityDaytrend(HomQualityDaytrend homQualityDaytrend) {
        return homQualityDaytrendMapper.updateHomQualityDaytrend(homQualityDaytrend);
    }

    /**
     * 批量删除质异日势
     *
     * @param ids 需要删除的质异日势主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityDaytrendByIds(Long[] ids) {
        return homQualityDaytrendMapper.deleteHomQualityDaytrendByIds(ids);
    }

    /**
     * 删除质异日势信息
     *
     * @param id 质异日势主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityDaytrendById(Long id) {
        return homQualityDaytrendMapper.deleteHomQualityDaytrendById(id);
    }
}
