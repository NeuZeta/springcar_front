package com.nzsoft.springcar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.fragments.newreservation.CarSelectionFragment;
import com.nzsoft.springcar.model.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private List<Car> cars;


    public CarListAdapter(Context contexto, List<Car> cars){
        this.cars = cars;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.row_model_car, null);

        TextView carModelText = (TextView) view.findViewById(R.id.idCarInfoModel);
        ImageView carImage = (ImageView) view.findViewById(R.id.idCarImage);

        Car car = cars.get(position);

        carModelText.setText(car.getModel());

        String imgURL = "https://springcarback.herokuapp.com/cars/image/" + car.getPhoto();

        Picasso.get().load(imgURL).into(carImage);

        TextView carPriceText = (TextView) view.findViewById(R.id.idCarInfoPriceBase);
        carPriceText.setText(car.getBasePrice() + "€");

        if (position%2 != 0){
            view.setBackgroundColor(view.getResources().getColor(R.color.colorPaleGray));
        }

        if (position == CarSelectionFragment.selectedCar){
            view.setBackgroundColor(view.getResources().getColor(R.color.colorPrimaryLight));
        }

        return view;
    }
}
