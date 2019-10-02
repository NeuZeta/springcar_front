package com.nzsoft.springcar.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.account.AccountEditFragment;
import com.nzsoft.springcar.fragments.account.AccountViewFragment;
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

    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if(!Utils.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

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

        updateActivityButtons ();

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
                                updateActivityButtons ();
                                fragmentTransaction.commit();
                                break;
        }
    }

    public void PerformAction (){

        switch (accountStatus) {

            case CREATE:        createClient ();
                                break;

            case VIEW:          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.idAccountDestino, new AccountEditFragment());
                                accountStatus = AccountStatus.UPDATE;
                                updateActivityButtons ();
                                fragmentTransaction.commit();
                                break;

            case UPDATE:
                break;
        }
    }

    public void enableActionBtn(){
        actionBtn.setVisibility(View.VISIBLE);
    }

    public void disableActionBtn() { actionBtn.setVisibility(View.INVISIBLE); }

    public Client getClient() {
        return client;
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

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.idAccountDestino, new AccountViewFragment());
                    accountStatus = AccountStatus.VIEW;
                    updateActivityButtons ();
                    fragmentTransaction.commit();

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    if (t instanceof SocketTimeoutException)
                    {
                        call.clone().enqueue(this);
                    }
                    else if (t instanceof IOException)
                    {
                        call.clone().enqueue(this);
                    }
                    else
                    {
                        Log.d("___", t.toString());
                    }
                }
            });
        }
    }

    private void updateActivityButtons (){
        switch (accountStatus){

            case CREATE:        actionBtn.setVisibility(View.INVISIBLE);
                                backBtn.setVisibility(View.INVISIBLE);
                                break;

            case VIEW:          actionBtn.setText(R.string.edit_btn);
                                backBtn.setText(R.string.home_btn);
                                backBtn.setVisibility(View.VISIBLE);
                                actionBtn.setVisibility(View.VISIBLE);
                                break;

            case UPDATE:        actionBtn.setVisibility(View.INVISIBLE);
                                backBtn.setText(R.string.back_btn);
                                backBtn.setVisibility(View.VISIBLE);
                                break;
        }
    }


}
