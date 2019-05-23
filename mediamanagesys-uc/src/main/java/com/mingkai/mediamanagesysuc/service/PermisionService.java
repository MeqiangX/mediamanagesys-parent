package com.mingkai.mediamanagesysuc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysmapper.manager.PermisionManager;
import com.mingkai.mediamanagesysmapper.model.Do.uc.PermisionDo;
import com.mingkai.mediamanagesysmapper.model.Po.uc.PermAddPo;
import com.mingkai.mediamanagesysmapper.model.Vo.uc.PermisionVo;
import com.mingkai.mediamanagesysmapper.utils.convert.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @description: 权限Service
 * @author: Created by 云风 on 2019-04-08 17:17
 */
@Service
public class PermisionService {

    @Autowired
    private PermisionManager permisionManager;

    public Page<PermisionVo> permisionList(Page page){
        Page<PermisionVo> permisionVoPage = (Page<PermisionVo>) permisionManager.page(page);
        return permisionVoPage;
    }

    @Transactional
    public Boolean addPermision(PermAddPo permAddPo){
        PermisionDo permisionDo  = new PermisionDo();
        permisionDo.setPermisionName(permAddPo.getPermisionName());
        permisionDo.setDescription(permAddPo.getDescription());
        return permisionManager.save(permisionDo);
    }

    public PermisionVo searchById(Integer id){
        PermisionDo permisionDo = permisionManager.getById(id);
        if (Objects.isNull(permisionDo)){
            throw new BizException("没有该权限的信息");
        }
        return ConvertUtil.convert(permisionDo,PermisionVo.class);
    }

}
