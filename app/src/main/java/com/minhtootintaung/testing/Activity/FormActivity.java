package com.minhtootintaung.testing.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minhtootintaung.testing.R;
import com.minhtootintaung.testing.ViewModel.MainViewModel;

public class FormActivity extends AppCompatActivity {

    MaterialButton goselectbtn;
    TextView totalpricetv;
    MainViewModel mainViewModel;
    TextInputEditText nameedt,emailedt;
    TextInputLayout textInputLayoutname,textInputLayoutemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        goselectbtn=findViewById(R.id.goselectbtn);
        totalpricetv=findViewById(R.id.totalpricetv);
        nameedt=findViewById(R.id.nameedt);
        emailedt=findViewById(R.id.emailedt);
        textInputLayoutname=findViewById(R.id.nametextInputLayout);
        textInputLayoutemail=findViewById(R.id.emailtextInputLayout2);
        goselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainViewModel.Validator(nameedt.getText().toString(),emailedt.getText().toString());
            }
        });

        Observers();

    }

    @Override
    protected void onResume() {
        super.onResume();

        totalpricetv.setText(new MainViewModel().totalprice==0?"":"Total price : "+new MainViewModel().totalprice+"");

    }

    private void Observers() {
      mainViewModel.NameError().observe(this, new Observer<String>() {
          @Override
          public void onChanged(String s) {

              textInputLayoutname.setError(s);
          }
      });
        mainViewModel.EmailError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                 textInputLayoutemail.setError(s);
            }
        });
        mainViewModel.Validation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)startActivity(new Intent(FormActivity.this,MapsActivity.class));
            }
        });

    }
}
