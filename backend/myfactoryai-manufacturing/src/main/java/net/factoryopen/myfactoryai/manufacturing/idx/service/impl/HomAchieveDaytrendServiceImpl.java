package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomAchieveDaytrend;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomAchieveDaytrendMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomAchieveDaytrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日现Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomAchieveDaytrendServiceImpl implements IHomAchieveDaytrendService {
    @Autowired
    private HomAchieveDaytrendMapper homAchieveDaytrendMapper;

    /**
     * 查询日现
     *
     * @param id 日现主键
     * @return 日现
     */
    @Override
    public HomAchieveDaytrend selectHomAchieveDaytrendById(Long id) {
        return homAchieveDaytrendMapper.selectHomAchieveDaytrendById(id);
    }

    /**
     * 查询日现列表
     *
     * @param homAchieveDaytrend 日现
     * @return 日现
     */
    @Override
    public List<HomAchieveDaytrend> selectHomAchieveDaytrendList(HomAchieveDaytrend homAchieveDaytrend) {
        return homAchieveDaytrendMapper.selectHomAchieveDaytrendList(homAchieveDaytrend);
    }

    /**
     * 新增日现
     *
     * @param homAchieveDaytrend 日现
     * @return 结果
     */
    @Override
    public int insertHomAchieveDaytrend(HomAchieveDaytrend homAchieveDaytrend) {
        return homAchieveDaytrendMapper.insertHomAchieveDaytrend(homAchieveDaytrend);
    }

    /**
     * 修改日现
     *
     * @param homAchieveDaytrend 日现
     * @return 结果
     */
    @Override
    public int updateHomAchieveDaytrend(HomAchieveDaytrend homAchieveDaytrend) {
        return homAchieveDaytrendMapper.updateHomAchieveDaytrend(homAchieveDaytrend);
    }

    /**
     * 批量删除日现
     *
     * @param ids 需要删除的日现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveDaytrendByIds(Long[] ids) {
        return homAchieveDaytrendMapper.deleteHomAchieveDaytrendByIds(ids);
    }

    /**
     * 删除日现信息
     *
     * @param id 日现主键
     * @return 结果
     */
    @Override
    public int deleteHomAchieveDaytrendById(Long id) {
        return homAchieveDaytrendMapper.deleteHomAchieveDaytrendById(id);
    }
}
