package com.mingkai.mediamanagesyscommon.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件Controller
 * @author: Created by 云风 on 2019-05-06 10:19
 */
@RestController
@Api(tags = API.COMMON_FILE)
@RequestMapping(API.API_COMMON_FILE)
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("图片上传")
    @PostMapping("/file-upload")
    public R<String> fileUpload(String id, MultipartFile file){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return fileService.fileUpload(id,file);
            }
        }.execute();
    }


}
