package net.factoryopen.myfactoryai.web.controller.common;

import com.ruoyi.common.core.domain.AjaxResult;
import net.factoryopen.myfactoryai.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class AvatarController {


    @Autowired
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/avatarEnabled")
    public AjaxResult avatarEnabled() {
        AjaxResult ajax = AjaxResult.success();
        boolean avatarEnabled = configService.selectAvatarEnabled();
        ajax.put("avatarEnabled", avatarEnabled);
        return ajax;
    }
}
