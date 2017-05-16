package cn.projects.com.projectsdemo.retrofit.data;

/**
 * Created by fengxing on 2017/4/22.
 */

public class UserTaskData {
    /**
     * taskId : 186422
     * lossItemTypeName : 其他
     * integratedFlag : 1
     * riskLevel : -4
     * callFlag : 0
     * vipFlag : 1
     * lossItemTypeCode : 9
     * scheduleType : 1
     * time : 1466647752000
     * licenseNo : 其他
     * readFlag : 0
     * registNo : RDZA201632010000001678
     * taskNo : 1
     * stateInfo : {"caseinfo":"0","document":"0","loss":"0","check":"1","task":"2"}
     * lFlag : L
     */

    private String taskId;
    private String lossItemTypeName;
    private String integratedFlag;
    private String riskLevel;
    private String callFlag;
    private String vipFlag;
    private String lossItemTypeCode;
    private String scheduleType;
    private long time;
    private String licenseNo;
    private String readFlag;
    private String registNo;
    private String taskNo;
    private StateInfoBean stateInfo;
    private String lFlag;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getLossItemTypeName() {
        return lossItemTypeName;
    }

    public void setLossItemTypeName(String lossItemTypeName) {
        this.lossItemTypeName = lossItemTypeName;
    }

    public String getIntegratedFlag() {
        return integratedFlag;
    }

    public void setIntegratedFlag(String integratedFlag) {
        this.integratedFlag = integratedFlag;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getCallFlag() {
        return callFlag;
    }

    public void setCallFlag(String callFlag) {
        this.callFlag = callFlag;
    }

    public String getVipFlag() {
        return vipFlag;
    }

    public void setVipFlag(String vipFlag) {
        this.vipFlag = vipFlag;
    }

    public String getLossItemTypeCode() {
        return lossItemTypeCode;
    }

    public void setLossItemTypeCode(String lossItemTypeCode) {
        this.lossItemTypeCode = lossItemTypeCode;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getRegistNo() {
        return registNo;
    }

    public void setRegistNo(String registNo) {
        this.registNo = registNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public StateInfoBean getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(StateInfoBean stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getLFlag() {
        return lFlag;
    }

    public void setLFlag(String lFlag) {
        this.lFlag = lFlag;
    }

    public static class StateInfoBean {
        /**
         * caseinfo : 0
         * document : 0
         * loss : 0
         * check : 1
         * task : 2
         */

        private String caseinfo;
        private String document;
        private String loss;
        private String check;
        private String task;

        public String getCaseinfo() {
            return caseinfo;
        }

        public void setCaseinfo(String caseinfo) {
            this.caseinfo = caseinfo;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

        public String getLoss() {
            return loss;
        }

        public void setLoss(String loss) {
            this.loss = loss;
        }

        public String getCheck() {
            return check;
        }

        public void setCheck(String check) {
            this.check = check;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }
    }

}
