package com.mingkai.commonserviceapi.service;


import com.mingkai.mappermodule.utils.file.OSSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件service
 * @author: Created by 云风 on 2019-05-06 10:20
 */
@Service
@Slf4j
public class FileService {

    @Autowired
    private OSSClientUtil ossClientUtil;


    // 图片上传   // eg： cinema/id/xxx.jpg  得到访问地址
    public String fileUpload(String id,MultipartFile file){

        String name = ossClientUtil.uploadImg2Oss(id,file);
        String imgUrl = ossClientUtil.getImgUrl(id,name);

        // 将img url 和本地的记录绑定
        return imgUrl;
    }

}
