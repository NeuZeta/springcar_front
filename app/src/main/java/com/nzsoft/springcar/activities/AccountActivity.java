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
import com.nzsoft.springcar.fragments.AccountViewFragment;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.retrofit.RetrofitHelper;
import com.nzsoft.springcar.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private AccountStatus accountStatus;
    private Button backBtn;
    private Button actionBtn;
    private Client client;
    private Client newClient;
    private View loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        loadingPanel = findViewById(R.id.loadingPanel_Account);
        loadingPanel.setVisibility(View.GONE);

        Long userId = Utils.loadPreferences(this);

        if (userId == 0) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.idAccountDestino, new AccountEditFragment());
            accountStatus = AccountStatus.CREATE;
            fragmentTransaction.commit();

        } else {

            accountStatus = AccountStatus.VIEW;

            Call<Client> call = RetrofitHelper.getApiRest().getClientById(userId);

            loadingPanel.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<Client>() {

                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    client = response.body();

                    loadingPanel.setVisibility(View.GONE);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.idAccountDestino, new AccountViewFragment());
                    fragmentTransaction.commit();

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                    if (t instanceof SocketTimeoutException)
                    {
                        Log.d("___", "Connection Timeout");
                        call.clone().enqueue(this);
                    }
                    else if (t instanceof IOException)
                    {
                        Log.d("___", "Timeout");
                        call.clone().enqueue(this);
                    }
                    else
                    {
                        Log.d("___", t.toString());
                    }
                }
            });
        }

        backBtn = findViewById(R.id.idBackBtn_Account);
        actionBtn = findViewById(R.id.idActionBtn_Account);

        switch (accountStatus){

            case CREATE:        actionBtn.setVisibility(View.INVISIBLE);
                                backBtn.setVisibility(View.INVISIBLE);
                                break;

            case VIEW:          actionBtn.setText("EDIT");
                                actionBtn.setVisibility(View.VISIBLE);
                                break;

            case UPDATE:        actionBtn.setVisibility(View.INVISIBLE);
                                break;
        }

    }

    public void GoBack (){
        Intent intent;

        switch (accountStatus) {

            case CREATE:        intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;

            case VIEW:          intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;

            case UPDATE:        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.idAccountDestino, new AccountViewFragment());
                                accountStatus = AccountStatus.VIEW;
                                fragmentTransaction.commit();
                                break;
        }
    }

    public void PerformAction (){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (accountStatus) {

            case CREATE:        createClient ();
                                fragmentTransaction.replace(R.id.idAccountDestino, new AccountViewFragment());
                                accountStatus = AccountStatus.VIEW;
                                fragmentTransaction.commit();
                                break;

            case VIEW:          fragmentTransaction.replace(R.id.idAccountDestino, new AccountEditFragment());
                                accountStatus = AccountStatus.UPDATE;
                                fragmentTransaction.commit();
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

    public Client getNewClient() {
        return newClient;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }

    public enum AccountStatus {
        CREATE, VIEW, UPDATE;
    }

    private void createClient () {

        if (newClient != null){

            Call<Client> call = RetrofitHelper.getApiRest().createClient(newClient);

            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    Log.d("***", "Client created, response: " + response.toString());

                    client = response.body();
                    Utils.savePreferences(getApplicationContext(), response.body().getId());
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Log.d("***", t.toString());

                }
            });
        }
    }

    private void updateClient (){
        if (newClient != null) {

            Call<Client> call = RetrofitHelper.getApiRest().updateClient(newClient);

            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });

        }
    }


}
