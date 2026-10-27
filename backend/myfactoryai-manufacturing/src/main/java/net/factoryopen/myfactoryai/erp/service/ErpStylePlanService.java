package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.domain.StylePlanRequest;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpStylePlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpStylePlanService {
    private final ErpStylePlanMapper mapper;
    private final ErpReadMapper read;
    public ErpStylePlanService(ErpStylePlanMapper mapper,ErpReadMapper read) { this.mapper=mapper; this.read=read; }

    public List<Map<String,Object>> bom(Long id) { return mapper.bom(id); }
    public List<Map<String,Object>> processes(Long id) { return mapper.processes(id); }
    public Map<String,Object> options() { return Map.of("materials",read.materials(),"processes",mapper.processOptions()); }

    @Transactional
    public void saveBom(Long styleId,StylePlanRequest.Bom request) {
        if (request == null || request.items() == null || request.items().isEmpty()) throw new ServiceException("BOM 至少有一项", ErpErrorCode.INVALID_PARAM.code());
        requireActiveStyle(styleId);
        Set<Long> seen = new HashSet<>();
        for (StylePlanRequest.BomLine line : request.items()) {
            if (line == null || line.materialId() == null || !seen.add(line.materialId())
                    || line.qty() == null || line.qty().signum() <= 0 || line.qty().scale() > 3
                    || line.lossRate() == null || line.lossRate().signum() < 0 || line.lossRate().compareTo(BigDecimal.valueOf(100)) > 0) throw new ServiceException("BOM 物料、用量或损耗率不合法", ErpErrorCode.INVALID_PARAM.code());
            if (mapper.materialUnit(line.materialId()) == null) throw new ServiceException("BOM 物料未启用", ErpErrorCode.INVALID_PARAM.code());
        }
        mapper.clearBom(styleId);
        for (StylePlanRequest.BomLine line : request.items()) mapper.addBom(styleId,line.materialId(),line.qty(),line.lossRate(),mapper.materialUnit(line.materialId()),SecurityUtils.getUsername());
    }

    @Transactional
    public void saveProcesses(Long styleId,StylePlanRequest.Processes request) {
        if (request == null || request.items() == null || request.items().isEmpty()) throw new ServiceException("至少填写一道工序", ErpErrorCode.INVALID_PARAM.code());
        requireActiveStyle(styleId);
        Set<Long> processes = new HashSet<>(); Set<Integer> sequence = new HashSet<>();
        for (StylePlanRequest.ProcessLine line : request.items()) {
            if (line == null || line.processId() == null || !processes.add(line.processId())
                    || line.seq() == null || line.seq() <= 0 || !sequence.add(line.seq())
                    || line.unitPrice() == null || line.unitPrice().signum() < 0 || line.unitPrice().scale() > 2
                    || mapper.activeProcess(line.processId()) != 1) throw new ServiceException("工序、顺序或工价不合法", ErpErrorCode.INVALID_PARAM.code());
        }
        mapper.clearProcesses(styleId);
        for (StylePlanRequest.ProcessLine line : request.items()) mapper.addProcess(styleId,line.processId(),line.seq(),line.unitPrice(),SecurityUtils.getUsername());
    }

    private void requireActiveStyle(Long styleId) {
        if (styleId == null || !Integer.valueOf(1).equals(mapper.lockStyle(styleId))) throw new ServiceException("款式未启用或不存在", ErpErrorCode.INVALID_PARAM.code());
    }
}
