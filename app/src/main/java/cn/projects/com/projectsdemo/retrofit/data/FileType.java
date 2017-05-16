package cn.projects.com.projectsdemo.retrofit.data;

import java.util.List;

/**
 * Created by fengxing on 2017/4/22.
 */

public class FileType {

    public List<FileTypeBean> fileType;
    public List<FileTypeMapBean> fileTypeMap;


    public static class FileTypeBean {
        /**
         * fileTypeCode : 2104
         * fileTypeName : 相关票据、事故证明
         * flag : 2100
         */

        private String fileTypeCode;
        private String fileTypeName;
        private String flag;

        public String getFileTypeCode() {
            return fileTypeCode;
        }

        public void setFileTypeCode(String fileTypeCode) {
            this.fileTypeCode = fileTypeCode;
        }

        public String getFileTypeName() {
            return fileTypeName;
        }

        public void setFileTypeName(String fileTypeName) {
            this.fileTypeName = fileTypeName;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

    public static class FileTypeMapBean {
        /**
         * categoryName : 基本单证
         * flag : 0
         * type : 1
         */

        private String categoryName;
        private String flag;
        private String type;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
