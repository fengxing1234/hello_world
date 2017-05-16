package cn.projects.com.projectsdemo.dspider.singlefactorymode;

/**
 * Created by fengxing on 17/3/16.
 */

public class Factory {

    public static Car getInstance(String name) {
        final Car car;
        switch (name) {
            case "BenChi":
                car = new BenChi();
                break;
            case "BaoMa":
                car = new BaoMa();
                break;
            default:
                throw new UnsupportedOperationException("不支持此类型");
        }
        return car;
    }

    public static Car getInstance2(String name){
        try {
            return  (Car) Class.forName(name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
