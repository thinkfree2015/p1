package com.ming800.organization.model;

import com.ming800.core.p.PConst;
import com.ming800.core.p.model.FileData;
import com.ming800.core.util.ApplicationContextUtil;
import com.ming800.core.util.AttachmentUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-28
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "core_p_attachment")
public class BranchAttachment {
    private String id;
    private String storeType;  // database,  disk,  ali, qiniu, baidu, url
    private String fileName;
    private String fileType;
    //    private long fileSize;   //文件大小
    private FileData data;       ///附件
    private String path;        //地址
    private Branch branch;           //附件所属机构
    private Branch branchModel;     //附件关联的对象类型
    private String generate;   //页面的现实内容  图片类型直接显示   其他类型设置为下载链接

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "store_type")
    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
//
//    @Column(name = "file_size")
//    public long getFileSize() {
//        return fileSize;
//    }
//
//    public void setFileSize(long fileSize) {
//        this.fileSize = fileSize;
//    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id")
    public FileData getData() {
        return data;
    }

    public void setData(FileData data) {
        this.data = data;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @OneToOne
    @JoinColumn(name = "model_id")
    public Branch getBranchModel() {
        return branchModel;
    }

    public void setBranchModel(Branch branchModel) {
        this.branchModel = branchModel;
    }

    @Transient
    public String getGenerate() {
        StringBuilder stringBuilder = new StringBuilder();
        if (storeType.equals(PConst.ATTACHMENT_STORETYPE_URL)) {
            stringBuilder.append("<a href=### onclick=art.dialog.open(\'")
                    .append(path)
                    .append("\')>")
                    .append(fileName)
                    .append("</a>");
        } else if (storeType.equals(PConst.ATTACHMENT_STORETYPE_DATABASE)) {
            if (fileType.startsWith("image")) {
                stringBuilder.append("<img src=\'").append(ApplicationContextUtil.getApplicationContext().getApplicationName()).append("/fileData/downLoad.do?id=").append(data.getId()).append("\'/>");
            } else {
                stringBuilder.append("<a href='").append(ApplicationContextUtil.getApplicationContext().getApplicationName()).append("/fileData/downLoad.do?id=").append(data.getId()).append("'>").append(fileName).append("</a>");
            }
        } else if (storeType.equals(PConst.ATTACHMENT_STORETYPE_ALI_CLOUD)) {
            if (fileType.startsWith("image")) {
                stringBuilder.append("<image src=\'")
                        .append("http://i.ming800.com/").append(path)
                        .append("\'/>");
            } else {
                stringBuilder.append("<a href='").append("http://i.ming800.com/").append(path).append("'>").append(fileName).append("</a>");
            }

        } else {
            if (fileType.startsWith("image")) {
                stringBuilder.append("<img src=\'").append(ApplicationContextUtil.getApplicationContext().getApplicationName()).append("/fileData/downLoadByPath.do?fileName=").append(fileName).append("&path=").append(path).append("\'/>");
            } else {
                stringBuilder.append("<a href='").append(ApplicationContextUtil.getApplicationContext().getApplicationName()).append("/fileData/downLoadByPath.do?fileName=").append(fileName).append("&path=").append(path).append("'>").append(fileName).append("</a>");
            }
        }
        return stringBuilder.toString();
    }
}
