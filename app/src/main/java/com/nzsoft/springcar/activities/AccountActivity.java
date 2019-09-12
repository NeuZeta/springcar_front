package com.nzsoft.springcar.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.AccountEditFragment;

public class AccountActivity extends AppCompatActivity {

    private AccountStatus accountStatus;
    private Button backBtn;
    private Button actionBtn;

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
            case CREATE:
                break;
            case VIEW:
                break;
            case UPDATE:
                break;
        }
    }

    public void PerformAction (){
        switch (accountStatus) {
            case CREATE:
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

    public enum AccountStatus {
        CREATE, VIEW, UPDATE;
    }
}
