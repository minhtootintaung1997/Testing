package com.minhtootintaung.testing.ViewModel;


import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minhtootintaung.testing.Model.AttractionModel;
import com.minhtootintaung.testing.api.Api;
import com.minhtootintaung.testing.api.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    Api api = new ApiClient().getClient().create(Api.class);

    MutableLiveData<List<AttractionModel>>mutableLiveData=new MutableLiveData<>();
    MutableLiveData<Boolean>mutableLiveDataSelect=new MutableLiveData<>();
    MutableLiveData<Boolean>mutableLiveDataValidationSuccess=new MutableLiveData<>();
   MutableLiveData<String>mutableLiveDataNameError=new MutableLiveData<>();
    MutableLiveData<String>mutableLiveDataEmailError=new MutableLiveData<>();

    List<AttractionModel>attractionModelList;
     public static int totalprice=0;

     public void Validator(String name, String email){
         Boolean nameboo=false,emailboo=false;
         if(!name.equals("")){
             nameboo=true;
             mutableLiveDataNameError.setValue("");
         }else {
             nameboo=false;
             mutableLiveDataNameError.setValue("Name Require");

         }
         if (!email.equals("")& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             emailboo=true;
             mutableLiveDataEmailError.setValue("");
         }
         else if(email.equals("")){
             emailboo=false;
             mutableLiveDataEmailError.setValue("Email Require");

         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             emailboo=false;
             mutableLiveDataEmailError.setValue("Email Format Require");
         }


         if(nameboo & emailboo)mutableLiveDataValidationSuccess.setValue(true);
         else mutableLiveDataValidationSuccess.setValue(false);
     }
     public LiveData<String>NameError(){
         return mutableLiveDataNameError;
     }
    public LiveData<String>EmailError(){
        return mutableLiveDataEmailError;
    }
    public LiveData<Boolean>Validation(){
        return mutableLiveDataValidationSuccess;
    }

    public void InitSelect(int position){
        mutableLiveDataSelect.setValue(false);
        AttractionModel model=attractionModelList.get(position);
        if(model.isSelected){
            model.isSelected=false;
            totalprice-=model.price;

        }
        else{
            model.isSelected=true;
            totalprice+=model.price;
        }

        mutableLiveDataSelect.setValue(true);
    }
    public LiveData<Boolean>GetSelect(){
        return mutableLiveDataSelect;
    }

    public void InitList(final Context context){
        attractionModelList = new ArrayList<>();
        totalprice=0;
        Call<List<AttractionModel>> call=api.getlist();
        call.enqueue(new Callback<List<AttractionModel>>() {
            @Override
            public void onResponse(Call<List<AttractionModel>> call, Response<List<AttractionModel>> response) {
                if(response.isSuccessful()){
                    if (response.body()!=null){
                        attractionModelList.addAll(response.body());
                        mutableLiveData.setValue(response.body());
                    }else {
                        Toast.makeText(context,"Try again",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AttractionModel>> call, Throwable t) {
                Toast.makeText(context,"Need Internet Connection.Try again",Toast.LENGTH_LONG).show();
            }
        });


    }

    public LiveData<List<AttractionModel>>GetList(){
        return mutableLiveData;
    }


}
