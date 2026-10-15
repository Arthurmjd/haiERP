package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQualityCausetrend;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomQualityCausetrendMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQualityCausetrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 质异原因Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomQualityCausetrendServiceImpl implements IHomQualityCausetrendService {
    @Autowired
    private HomQualityCausetrendMapper homQualityCausetrendMapper;

    /**
     * 查询质异原因
     *
     * @param id 质异原因主键
     * @return 质异原因
     */
    @Override
    public HomQualityCausetrend selectHomQualityCausetrendById(Long id) {
        return homQualityCausetrendMapper.selectHomQualityCausetrendById(id);
    }

    /**
     * 查询质异原因列表
     *
     * @param homQualityCausetrend 质异原因
     * @return 质异原因
     */
    @Override
    public List<HomQualityCausetrend> selectHomQualityCausetrendList(HomQualityCausetrend homQualityCausetrend) {
        return homQualityCausetrendMapper.selectHomQualityCausetrendList(homQualityCausetrend);
    }

    /**
     * 新增质异原因
     *
     * @param homQualityCausetrend 质异原因
     * @return 结果
     */
    @Override
    public int insertHomQualityCausetrend(HomQualityCausetrend homQualityCausetrend) {
        return homQualityCausetrendMapper.insertHomQualityCausetrend(homQualityCausetrend);
    }

    /**
     * 修改质异原因
     *
     * @param homQualityCausetrend 质异原因
     * @return 结果
     */
    @Override
    public int updateHomQualityCausetrend(HomQualityCausetrend homQualityCausetrend) {
        return homQualityCausetrendMapper.updateHomQualityCausetrend(homQualityCausetrend);
    }

    /**
     * 批量删除质异原因
     *
     * @param ids 需要删除的质异原因主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityCausetrendByIds(Long[] ids) {
        return homQualityCausetrendMapper.deleteHomQualityCausetrendByIds(ids);
    }

    /**
     * 删除质异原因信息
     *
     * @param id 质异原因主键
     * @return 结果
     */
    @Override
    public int deleteHomQualityCausetrendById(Long id) {
        return homQualityCausetrendMapper.deleteHomQualityCausetrendById(id);
    }
}
