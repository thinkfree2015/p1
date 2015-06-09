package com.ming800.core.p.controller;

import com.ming800.core.p.model.FileUploadRecord;
import com.ming800.core.p.model.M8idGenerator;
import com.ming800.core.p.service.AliOssUploadManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.SystemIdUtil;
import com.ming800.organization.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-11-5
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/ossUpload")
public class OssUploadController {

    @Autowired
    private BaseManager baseManager;
    @Autowired
    private AliOssUploadManager ossUploadManager;

    @RequestMapping("/fileUpload.do")
    @ResponseBody
    public Object fileUpload(HttpServletRequest request, MultipartRequest multipartRequest) throws Exception {

        String objectId = request.getParameter("objectId");
        String objectType = request.getParameter("objectType");

        String branchId = request.getParameter("branchId");

        FileUploadRecord fileUploadRecord = null;
        if (branchId != null && !branchId.equals("")) {

            fileUploadRecord = new FileUploadRecord();

            Branch branch = (Branch) baseManager.getObject(Branch.class.getName(), branchId);

            List<MultipartFile> multipartFileList = multipartRequest.getFiles("Filedata");
            if (multipartFileList != null && multipartFileList.size() > 0) {
                for (MultipartFile multipartFile : multipartFileList) {
                    String realFileName = multipartFile.getOriginalFilename();

                    String type = "";
                    String fileName = realFileName.substring(realFileName.lastIndexOf(".")).toLowerCase();

                    /*F4V AVI WMV MPEG H.264 MPG WAV*/
                    if (fileName.equals(".mp4") || fileName.equals(".avi") || fileName.equals(".wmv") || fileName.equals(".mpg") || fileName.equals(".wav") || fileName.equals(".flv")) {
                        type = "video";
                    } else if (fileName.equals(".jpg") || fileName.equals(".gif") || fileName.equals(".png") || fileName.equals(".bmp") || fileName.equals(".jpeg")) {
                        type = "jpg";
                    } else {
                        type = "other";       //fileName.equals(".mp3")   mp3 也作为普通文件
                    }
                    String uploadObjectName = "dish/" + SystemIdUtil.generateIdName() + multipartFile.getOriginalFilename();
//                  uploadObjectName = java.net.URLEncoder.encode(uploadObjectName, "utf-8");
                    Boolean flag = ossUploadManager.uploadFile(multipartFile, "m80", uploadObjectName);

                    if (flag) {

                        fileUploadRecord.setName(realFileName);
                        fileUploadRecord.setUrl(uploadObjectName);
                        fileUploadRecord.setBranch(branch);
                        fileUploadRecord.setObjectId(objectId);
                        fileUploadRecord.setObjectType(objectType);
                        fileUploadRecord.setType(type);

                        M8idGenerator.setSerial(branch.getSerial());
                        baseManager.saveOrUpdate(FileUploadRecord.class.getName(), fileUploadRecord);
                    }
                }
            }
        }

        return fileUploadRecord;
    }


}


