package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityResultrend;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomQualityResultrendMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityResultrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 质异结果Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomQualityResultrendServiceImpl implements IHomQualityResultrendService {
    @Autowired
    private HomQualityResultrendMapper homQualityResultrendMapper;

    /**
     * 查询质异结果
     *
     * @param id 质异结果主键
     * @return 质异结果
     */
    @Override
    public HomQualityResultrend selectHomQualityResultrendById(Long id) {
        return homQualityResultrendMapper.selectHomQualityResultrendById(id);
    }

    /**
     * 查询质异结果列表
     *
     * @param homQualityResultrend 质异结果
     * @return 质异结果
     */
    @Override
    public List<HomQualityResultrend> selectHomQualityResultrendList(HomQualityResultrend homQualityResultrend) {
        return homQualityResultrendMapper.selectHomQualityResultrendList(homQualityResultrend);
    }

    /**
     * 新增质异结果
     *
     * @param homQualityResultrend 质异结果
     * @return 结果
     */
    @Override
    public int insertHomQualityResultrend(HomQualityResultrend homQualityResultrend) {
        return homQualityResultrendMapper.insertHomQualityResultrend(homQualityResultrend);
    }

    /**
     * 修改质异结果
     *
     * @param homQualityResultrend 质异结果
     * @return 结果
     */
    @Override
    public int updateHomQualityResultrend(HomQualityResultrend homQualityResultrend) {
        return homQualityResultrendMapper.updateHomQualityResultrend(homQualityResultrend);
    }

    /**
     * 批量删除质异结果
     *
     * @param ids 需要删除的质异结果主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityResultrendByIds(Long[] ids) {
        return homQualityResultrendMapper.deleteHomQualityResultrendByIds(ids);
    }

    /**
     * 删除质异结果信息
     *
     * @param id 质异结果主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityResultrendById(Long id) {
        return homQualityResultrendMapper.deleteHomQualityResultrendById(id);
    }
}
