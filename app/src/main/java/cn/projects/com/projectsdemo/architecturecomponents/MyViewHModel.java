package cn.projects.com.projectsdemo.architecturecomponents;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by fengxing on 2017/5/24.
 */

public class MyViewHModel extends ViewModel {


    private PostalCodeRepository repository;

    private final MutableLiveData<String> addressInput = new MutableLiveData<>();

//    private final LiveData<String> code = Transformations.switchMap(addressInput,(address)->{
//        repository.getPostCode(address);
//    });

    public MyViewHModel(PostalCodeRepository postalCodeRepository){
        this.repository= postalCodeRepository;
    }


    private String getPostalCode(String address) {
        // DON'T DO THIS
        return repository.getPostCode(address);
    }






    //-----------------------------------


    private MutableLiveData<List<User>> users;

    public LiveData<List<User>> getUsers(){
        if(users == null){
            users = new MutableLiveData<>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        //do async operation to fetch users
    }
}
