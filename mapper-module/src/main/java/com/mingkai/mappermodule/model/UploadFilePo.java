package com.mingkai.mappermodule.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 17:55
 */
@Data
@ApiModel("文件上传带参数")
public class UploadFilePo {

    private String id;

    private MultipartFile file;

}
