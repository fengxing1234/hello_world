package cn.projects.com.projectsdemo.architecturecomponents;

/**
 * Created by fengxing on 2017/5/24.
 */

public class PostalCodeRepository {
    private String postCode;
    private String address;

    public String getPostCode(String address) {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
