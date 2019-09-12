package com.nzsoft.springcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.AccountActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountEditFragment extends Fragment {

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText numberText;
    private EditText addressText;
    private EditText zipCodeText;
    private EditText cityText;
    private EditText countryText;
    private EditText phoneText;
    private EditText emailText;
    private EditText userNameText;
    private EditText passwordText;
    private EditText confirmPasswordText;



    private List<EditText> textFields;

    public AccountEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_edit, container, false);

        //Get UI references
        firstNameText = (EditText) view.findViewById(R.id.idNameText);
        lastNameText = (EditText) view.findViewById(R.id.idLastNameText);
        numberText = (EditText) view.findViewById(R.id.idIDNumberText);
        addressText = (EditText) view.findViewById(R.id.idAddressText);
        zipCodeText = (EditText) view.findViewById(R.id.idZipCodeText);
        cityText = (EditText) view.findViewById(R.id.idCityText);
        countryText = (EditText) view.findViewById(R.id.idCountryText);
        phoneText = (EditText) view.findViewById(R.id.idPhoneText);
        emailText = (EditText) view.findViewById(R.id.idEmailText);
        userNameText = (EditText) view.findViewById(R.id.idUserNameText);
        passwordText = (EditText) view.findViewById(R.id.idPasswordText);
        confirmPasswordText = (EditText) view.findViewById(R.id.idConfirmPasswordText);


        //Initialize editText list
        textFields = new ArrayList<>();

        textFields.add(firstNameText);
        textFields.add(lastNameText);
        textFields.add(numberText);
        textFields.add(addressText);
        textFields.add(zipCodeText);
        textFields.add(cityText);
        textFields.add(countryText);
        textFields.add(phoneText);
        textFields.add(emailText);
        textFields.add(userNameText);
        textFields.add(passwordText);
        textFields.add(confirmPasswordText);

        for (EditText field : textFields){
            field.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if (allDataFilledUp()){
                        ((AccountActivity)getActivity()).enableActionBtn();
                    } else {
                        ((AccountActivity)getActivity()).disableActionBtn();
                    }
                }
            });
        }
        return view;
    }

    private boolean allDataFilledUp(){

        for (EditText field : textFields){
            if (TextUtils.isEmpty(field.getText())){
                return false;
            }
        }
        return true;
    }

}
