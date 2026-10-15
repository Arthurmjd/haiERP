package net.factoryopen.myfactoryai.manufacturing.pln.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

  import net.factoryopen.core.myfactoryai.manufacturing.pln.PlnMlDispatchingAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.pln.mapper.PlnMlDispatchingMapper;
import net.factoryopen.myfactoryai.manufacturing.pln.domain.PlnMlDispatching;
import net.factoryopen.myfactoryai.manufacturing.pln.service.IPlnMlDispatchingService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 上线派工Service业务层处理
 * 
 * @author jitfactory-generator
 * @date 2024-07-04
 */
@Service
public class PlnMlDispatchingServiceImpl implements IPlnMlDispatchingService 
{
    @Autowired(required = false)
    private PlnMlDispatchingMapper plnMlDispatchingMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private PlnMlDispatchingAlgorithm plnMlDispatchingAlgorithm;

    /**
     * 查询上线派工
     * 
     * @param id 上线派工主键
     * @return 上线派工
     */
    @Override
    public PlnMlDispatching selectPlnMlDispatchingById(Long id)
    {
        return plnMlDispatchingMapper.selectPlnMlDispatchingById(id);
    }

    /**
     * 查询上线派工列表
     * 
     * @param plnMlDispatching 上线派工
     * @return 上线派工
     */
    @Override
    public List<PlnMlDispatching> selectPlnMlDispatchingList(PlnMlDispatching plnMlDispatching)
    {
        return plnMlDispatchingMapper.selectPlnMlDispatchingList(plnMlDispatching);
    }

    /**
     * 新增上线派工
     * 
     * @param plnMlDispatching 上线派工
     * @return 结果
     */
    @Override
    public int insertPlnMlDispatching(PlnMlDispatching plnMlDispatching)
    {
        plnMlDispatching.setCreateTime(DateUtils.getNowDate());
        return plnMlDispatchingMapper.insertPlnMlDispatching(plnMlDispatching);
    }

    /**
     * 修改上线派工
     * 
     * @param plnMlDispatching 上线派工
     * @return 结果
     */
    @Override
    public int updatePlnMlDispatching(PlnMlDispatching plnMlDispatching)
    {
        plnMlDispatching.setUpdateTime(DateUtils.getNowDate());
        return plnMlDispatchingMapper.updatePlnMlDispatching(plnMlDispatching);
    }

    /**
     * 批量删除上线派工
     * 
     * @param ids 需要删除的上线派工主键
     * @return 结果
     */
    @Override
    public int deletePlnMlDispatchingByIds(Long[] ids)
    {
        return plnMlDispatchingMapper.deletePlnMlDispatchingByIds(ids);
    }

    /**
     * 删除上线派工信息
     * 
     * @param id 上线派工主键
     * @return 结果
     */
    @Override
    public int deletePlnMlDispatchingById(Long id)
    {
        return plnMlDispatchingMapper.deletePlnMlDispatchingById(id);
    }

    /**
     * 批派
     *
     * @param plnMlDispatching 批派
     * @return 结果
     */
    @Override
    public int dispatchMultipleMl(Long[] ids, PlnMlDispatching plnMlDispatching) throws Exception{
        try{
            return plnMlDispatchingAlgorithm.dispatchMultipleMl(ids, plnMlDispatching);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 单派
     *
     * @param plnMlDispatching 单派
     * @return 结果
     */
    @Override
    public int dispatchSingleMl(PlnMlDispatching plnMlDispatching) throws Exception{
        try{
            return plnMlDispatchingAlgorithm.dispatchSingleMl(plnMlDispatching);
        }catch (Exception ex){
            throw ex;
        }
    }

}
