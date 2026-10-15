package net.factoryopen.myfactoryai.manufacturing.idx.service.impl;

import net.factoryopen.myfactoryai.manufacturing.idx.domain.HomQcd;
import net.factoryopen.myfactoryai.manufacturing.idx.mapper.HomQcdMapper;
import net.factoryopen.myfactoryai.manufacturing.idx.service.IHomQcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QCDService业务层处理
 *
 * @author ruoyi
 * @date 2023-06-07
 */
@Service
public class HomQcdServiceImpl implements IHomQcdService {
    @Autowired
    private HomQcdMapper homQcdMapper;

    /**
     * 查询QCD
     *
     * @param id QCD主键
     * @return QCD
     */
    @Override
    public HomQcd selectHomQcdById(Long id) {
        return homQcdMapper.selectHomQcdById(id);
    }

    /**
     * 查询QCD列表
     *
     * @param homQcd QCD
     * @return QCD
     */
    @Override
    public List<HomQcd> selectHomQcdList(HomQcd homQcd) {
        return homQcdMapper.selectHomQcdList(homQcd);
    }

    /**
     * 新增QCD
     *
     * @param homQcd QCD
     * @return 结果
     */
    @Override
    public int insertHomQcd(HomQcd homQcd) {
        return homQcdMapper.insertHomQcd(homQcd);
    }

    /**
     * 修改QCD
     *
     * @param homQcd QCD
     * @return 结果
     */
    @Override
    public int updateHomQcd(HomQcd homQcd) {
        return homQcdMapper.updateHomQcd(homQcd);
    }

    /**
     * 批量删除QCD
     *
     * @param ids 需要删除的QCD主键
     * @return 结果
     */
    @Override
    public int deleteHomQcdByIds(Long[] ids) {
        return homQcdMapper.deleteHomQcdByIds(ids);
    }

    /**
     * 删除QCD信息
     *
     * @param id QCD主键
     * @return 结果
     */
    @Override
    public int deleteHomQcdById(Long id) {
        return homQcdMapper.deleteHomQcdById(id);
    }
}
