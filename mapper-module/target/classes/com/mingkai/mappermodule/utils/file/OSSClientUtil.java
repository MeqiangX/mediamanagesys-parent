package com.mingkai.mappermodule.utils.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.dtstack.plat.lang.base.Strings;
import com.dtstack.plat.lang.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @description: OSS 图片上传工具类
 * @author: Created by 云风 on 2019-05-06 10:45
 */
@Slf4j
@Component
public class OSSClientUtil {

    private String accessKeyId = "LTAI7suwceOqdho7";

    private String accessKeySecret = "1uCBXdyNUTWymjF2E8RuNFu4B5o1hD";

    private String bucket = "mediasys-image";

    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

    private String filedir = "cinema/";

    private OSSClient ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String id,String url) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, id, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new BizException("图片上传失败");
        }
    }


    public String uploadImg2Oss(String id,MultipartFile file) {
        if (file.getSize() > 1024 * 1024 * 10) {
            throw new BizException("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, id, name);
            return name;
        } catch (Exception e) {
            throw new BizException("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String id,String fileUrl) {
        if (!Strings.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            if (Objects.nonNull(id) && id != ""){
                return this.getUrl(this.filedir + id + "/" + split[split.length - 1]);
            }
            return this.getUrl(this.filedir  + split[split.length - 1]);

        }
        return null;
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的  每次都清空当前id 下的图片
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String id,String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件

            // 文件夹不存在 时候 创建
            String folderName = filedir;
            if (Objects.nonNull(id) && id != ""){
                folderName = filedir + id + "/";
            }

            boolean isFolderExist = ossClient.doesObjectExist(bucket, folderName);
            //没有该prodId文件夹 --> 创建
            if (!isFolderExist){
                log.info("没有此{}文件夹,执行创建",folderName);
                //创建文件夹
                byte[] buffer = new byte[0];
                ByteArrayInputStream in = new ByteArrayInputStream(buffer);
                ObjectMetadata object = new ObjectMetadata();
                object.setContentLength(0);
                try{
                    ossClient.putObject(bucket, folderName, in, object);
                }catch (Exception e){
                    log.error("创建文件夹失败{}",e);
                    throw new BizException("上传失败{}",e);
                }finally{
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.error("关闭流失败{}",e);
                        throw new  BizException("上传失败{}",e);
                    }
                }
            }



            //遍历文件夹
            /*ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucket);
            listObjectsRequest.setPrefix(folderName);
            ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);

            //得到所有 prodId/ 下的 所有文件
            List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();

            if (Objects.nonNull(objectSummaries) || objectSummaries.size() != 0) {
                List<String> keys = objectSummaries.stream().map(OSSObjectSummary::getKey).collect(Collectors.toList());
                // 执行删除
                DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(keys));
            }*/

            PutObjectResult putResult = ossClient.putObject(bucket, folderName + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.info("上传失败 :" + e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucket, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

}
