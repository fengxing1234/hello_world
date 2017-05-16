package cn.projects.com.projectsdemo.retrofit.data;

/**
 * Created by fengxing on 2017/4/22.
 */

public class BaseResult<T> {

    /**
     * flag : true
     * data : [{"taskId":"186422","lossItemTypeName":"其他","integratedFlag":"1","riskLevel":"-4","callFlag":"0","vipFlag":"1","lossItemTypeCode":"9","scheduleType":"1","time":1466647752000,"licenseNo":"其他","readFlag":"0","registNo":"RDZA201632010000001678","taskNo":"1","stateInfo":{"caseinfo":"0","document":"0","loss":"0","check":"1","task":"2"},"lFlag":"L"}]
     * state : 200
     * msg : 成功返回数据
     */

    private boolean flag;
    private String state;
    private String msg;
    private T data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
