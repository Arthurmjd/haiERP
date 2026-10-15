package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveLotrend;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomAchieveLotrendMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveLotrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 批次现Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomAchieveLotrendServiceImpl implements IHomAchieveLotrendService {
    @Autowired
    private HomAchieveLotrendMapper homAchieveLotrendMapper;

    /**
     * 查询批次现
     *
     * @param id 批次现主键
     * @return 批次现
     */
    @Override
    public HomAchieveLotrend selectHomAchieveLotrendById(Long id) {
        return homAchieveLotrendMapper.selectHomAchieveLotrendById(id);
    }

    /**
     * 查询批次现列表
     *
     * @param homAchieveLotrend 批次现
     * @return 批次现
     */
    @Override
    public List<HomAchieveLotrend> selectHomAchieveLotrendList(HomAchieveLotrend homAchieveLotrend) {
        return homAchieveLotrendMapper.selectHomAchieveLotrendList(homAchieveLotrend);
    }

    /**
     * 新增批次现
     *
     * @param homAchieveLotrend 批次现
     * @return 结果
     */
    @Override
    public int insertHomAchieveLotrend(HomAchieveLotrend homAchieveLotrend) {
        return homAchieveLotrendMapper.insertHomAchieveLotrend(homAchieveLotrend);
    }

    /**
     * 修改批次现
     *
     * @param homAchieveLotrend 批次现
     * @return 结果
     */
    @Override
    public int updateHomAchieveLotrend(HomAchieveLotrend homAchieveLotrend) {
        return homAchieveLotrendMapper.updateHomAchieveLotrend(homAchieveLotrend);
    }

    /**
     * 批量删除批次现
     *
     * @param ids 需要删除的批次现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveLotrendByIds(Long[] ids) {
        return homAchieveLotrendMapper.deleteHomAchieveLotrendByIds(ids);
    }

    /**
     * 删除批次现信息
     *
     * @param id 批次现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveLotrendById(Long id) {
        return homAchieveLotrendMapper.deleteHomAchieveLotrendById(id);
    }
}
