package com.nzsoft.springcar.fragments.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.AccountActivity;
import com.nzsoft.springcar.model.Client;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountViewFragment extends Fragment {


    public AccountViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.fragment_account_view, container, false);

        ((AccountActivity)getActivity()).enableActionBtn();

        Client client = ((AccountActivity)getActivity()).getClient();

        TextView title = (TextView) view.findViewById(R.id.idRegistrationTitle);
        title.setText(client.getUserName().toUpperCase());

        TextView firstName = (TextView) view.findViewById(R.id.idFirstNameView);
        firstName.setText(client.getFirstName());

        TextView lastName = (TextView) view.findViewById(R.id.idLastNameView);
        lastName.setText(client.getLastName());

        TextView idNumber = (TextView) view.findViewById(R.id.idIdNumberView);
        idNumber.setText(client.getIdNumber());

        TextView address = (TextView) view.findViewById(R.id.idAddressView);
        address.setText(client.getLocation().getAddress());

        TextView zipCode = (TextView) view.findViewById(R.id.idZipCodeView);
        zipCode.setText(client.getLocation().getZipCode());

        TextView city = (TextView) view.findViewById(R.id.idCityView);
        city.setText(client.getLocation().getCity());

        TextView country = (TextView) view.findViewById(R.id.idCountryView);
        country.setText(client.getLocation().getCountry());

        TextView phoneNo = (TextView) view.findViewById(R.id.idPhoneView);
        phoneNo.setText(client.getContact().getPhoneNumber());

        TextView email = (TextView) view.findViewById(R.id.idAEmailView);
        email.setText(client.getContact().getEmail());

        TextView userName = (TextView) view.findViewById(R.id.idUserNameView);
        userName.setText(client.getUserName());

        TextView password = (TextView) view.findViewById(R.id.idPasswordView);
        password.setText(client.getPassword());

        return view;
    }

}
