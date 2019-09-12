package com.nzsoft.springcar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.AccountEditFragment;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.nzsoft.springcar.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private AccountStatus accountStatus;
    private Button backBtn;
    private Button actionBtn;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.idAccountDestino, new AccountEditFragment());
            accountStatus = AccountStatus.CREATE;
            fragmentTransaction.commit();
        }

        backBtn = findViewById(R.id.idBackBtn_Account);
        actionBtn = findViewById(R.id.idActionBtn_Account);

        switch (accountStatus){
            case CREATE:
                actionBtn.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                break;
            case VIEW:
                actionBtn.setVisibility(View.VISIBLE);
                break;
            case UPDATE:
                actionBtn.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void GoBack (){
        switch (accountStatus) {
            case CREATE:        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(homeIntent);
                                break;
            case VIEW:
                break;
            case UPDATE:
                break;
        }
    }

    public void PerformAction (){
        switch (accountStatus) {
            case CREATE:        createClient ();
                                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(homeIntent);
                                break;

            case VIEW:
                break;
            case UPDATE:
                break;
        }
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void enableActionBtn(){
        actionBtn.setVisibility(View.VISIBLE);
    }

    public void disableActionBtn() { actionBtn.setVisibility(View.INVISIBLE); }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public enum AccountStatus {
        CREATE, VIEW, UPDATE;
    }

    private void createClient (){
        if (client != null){
            Call<Client> call = RetrofitHelper.getApiRest().createClient(client);

            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    Log.d("***", "Client created, response: " + response.toString());

                    Utils.savePreferences(getApplicationContext(), response.body().getId());
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Log.d("***", t.toString());

                }
            });
        }
    }

}
