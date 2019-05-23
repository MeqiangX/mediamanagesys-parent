package com.mingkai.mediamanagesyscommonapp.service;


import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesyscommonapp.feigns.CommonRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 16:56
 */
@Service
public class FileRpcService {

    @Autowired
    private CommonRpcFeign commonRpcFeign;


    public String fileUpload(String id, MultipartFile file){

        // 包装成object;

        return CheckOfR.check(commonRpcFeign.fileUpload(id,file));
    }

}
