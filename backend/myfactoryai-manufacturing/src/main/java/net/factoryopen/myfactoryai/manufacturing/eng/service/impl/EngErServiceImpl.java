package net.factoryopen.myfactoryai.manufacturing.eng.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    import net.factoryopen.core.myfactoryai.manufacturing.eng.EngErAlgorithm;

import net.factoryopen.myfactoryai.manufacturing.eng.mapper.EngErMapper;
import net.factoryopen.myfactoryai.manufacturing.eng.domain.EngEr;
import net.factoryopen.myfactoryai.manufacturing.eng.service.IEngErService;

import net.factoryopen.myfactoryai.system.domain.SysBillFrozen;
import net.factoryopen.myfactoryai.system.mapper.SysBillFrozenMapper;

/**
 * 工艺处理请求Service业务层处理
 * 
 * @author admin
 * @date 2024-12-12
 */
@Service
public class EngErServiceImpl implements IEngErService 
{
    @Autowired(required = false)
    private EngErMapper engErMapper;

    @Autowired(required = false)
    private SysBillFrozenMapper sysBillFrozenMapper;

    @Autowired(required = false)
    private EngErAlgorithm engErAlgorithm;

    /**
     * 查询工艺处理请求
     * 
     * @param id 工艺处理请求主键
     * @return 工艺处理请求
     */
    @Override
    public EngEr selectEngErById(Long id)
    {
        return engErMapper.selectEngErById(id);
    }

    /**
     * 查询工艺处理请求列表
     * 
     * @param engEr 工艺处理请求
     * @return 工艺处理请求
     */
    @Override
    public List<EngEr> selectEngErList(EngEr engEr)
    {
        return engErMapper.selectEngErList(engEr);
    }

    /**
     * 新增工艺处理请求
     * 
     * @param engEr 工艺处理请求
     * @return 结果
     */
    @Override
    public int insertEngEr(EngEr engEr)
    {
        engEr.refreshCreatingStamp();
        return engErMapper.insertEngEr(engEr);
    }

    /**
     * 修改工艺处理请求
     * 
     * @param engEr 工艺处理请求
     * @return 结果
     */
    @Override
    public int updateEngEr(EngEr engEr)
    {
        engEr.refreshUpdatingStamp();
        return engErMapper.updateEngEr(engEr);
    }

    /**
     * 批量删除工艺处理请求
     * 
     * @param ids 需要删除的工艺处理请求主键
     * @return 结果
     */
    @Override
    public int deleteEngErByIds(Long[] ids)
    {
        return engErMapper.deleteEngErByIds(ids);
    }

    /**
     * 删除工艺处理请求信息
     * 
     * @param id 工艺处理请求主键
     * @return 结果
     */
    @Override
    public int deleteEngErById(Long id)
    {
        return engErMapper.deleteEngErById(id);
    }

     /**
      * 完成
      *
      * @param engEr 完成
      * @return 结果
      */
     @Override
     public int finish(EngEr engEr){
         Long id = engEr.getId();
         EngEr obj = engErMapper.selectEngErById(id);

         obj.setStatus(11);
         obj.refreshUpdatingStamp();
         return engErMapper.updateEngEr(obj);
     }
    /**
     * 生成料号
     *
     * @param engEr 生成料号
     * @return 结果
     */
    @Override
    public int generateCode(EngEr engEr) throws Exception{
        try{
            return engErAlgorithm.generateCode(engEr);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 维护BOM
     *
     * @param engEr 维护BOM
     * @return 结果
     */
    @Override
    public int updateBom(EngEr engEr) throws Exception{
        try{
            engEr.refreshUpdatingStamp();
            return engErAlgorithm.updateBom(engEr);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 维护路线
     *
     * @param engEr 维护路线
     * @return 结果
     */
    @Override
    public int updateRouting(EngEr engEr) throws Exception{
        try{
            engEr.refreshUpdatingStamp();
            return engErAlgorithm.updateRouting(engEr);
        }catch (Exception ex){
            throw ex;
        }
    }
    /**
     * 维护文档
     *
     * @param engEr 维护文档
     * @return 结果
     */
    @Override
    public int updateDocs(EngEr engEr) throws Exception{
        try{
            engEr.refreshUpdatingStamp();
            return engErAlgorithm.updateDocs(engEr);
        }catch (Exception ex){
            throw ex;
        }
    }

}
