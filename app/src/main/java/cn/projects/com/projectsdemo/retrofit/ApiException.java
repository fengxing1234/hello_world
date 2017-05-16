package cn.projects.com.projectsdemo.retrofit;

/**
 * Created by fengxing on 2017/4/24.
 */

class ApiException extends Throwable {

    public static final String SPLIT = "fengxing";

    private static final String TAG = "ApiException";

    private static String mes;
    private String code;
    private String m;

    public ApiException(String s) {
        super(s);
//        String[] codeMes = s.split(SPLIT);
//        code = codeMes[0];
//        m = codeMes[1];
//        Log.d(TAG, "code: "+ code + "  mes : "+ m);
//        String exceptionMessage = getExceptionMessage(Integer.parseInt(code));
//        Log.d(TAG, "ApiException: "+exceptionMessage);
    }

    public ApiException(int code){
        this(getExceptionMessage(code));
    }

    public static String getExceptionMessage(int code){
        switch (code){
            case 404:
                mes = "404啦";
                break;
            case 500:
                mes = "500啦";
                default:
                    mes = code+"啦";
                    break;
        }
        return mes;
    }
}
