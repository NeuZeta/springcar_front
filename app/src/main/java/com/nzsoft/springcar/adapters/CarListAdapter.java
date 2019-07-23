package com.nzsoft.springcar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.model.Car;

import java.util.List;

public class CarListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private List<Car> cars;
    private Context contexto;

    public CarListAdapter(Context contexto, List<Car> cars){
        this.contexto = contexto;
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

        TextView carModelText = (TextView) view.findViewById(R.id.idCarModel);
        final ImageView carImage = (ImageView) view.findViewById(R.id.idCarImage);

        Car car = cars.get(position);

        carModelText.setText(car.getModel());

        return null;
    }
}
