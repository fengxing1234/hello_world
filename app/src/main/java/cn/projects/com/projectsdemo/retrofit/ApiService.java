package cn.projects.com.projectsdemo.retrofit;

import cn.projects.com.projectsdemo.retrofit.data.BaseResult;
import cn.projects.com.projectsdemo.retrofit.data.FileType;
import cn.projects.com.projectsdemo.retrofit.data.MyData;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by fengxing on 2017/4/22.
 */

public interface ApiService {

    @GET("userTasks")
    Observable<MyData> getUserTask();

    @GET("fileType")
    Observable<BaseResult<FileType>> getFileType();

//    @GET("top250")
//    Observable<BaseResult<List<TopMovie>>> getMovieTop(@Query("start") int start,@Query("count") int count);
}
