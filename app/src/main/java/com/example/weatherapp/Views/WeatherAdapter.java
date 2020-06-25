package com.example.weatherapp.Views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapp.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.myViewHolder> {
    private Context context;
    private List<Uri> albumList = new ArrayList<>();

    public WeatherAdapter(Context context, List<Uri> mylist) {
        this.context = context;
        this.albumList.addAll(mylist);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Uri current = albumList.get(position);

        holder.img.setImageURI(current);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, PhotoActivity.class);
                myIntent.putExtra("key", current);
                myIntent.putExtra("WeatherTxtKey", "fullsizephoto");
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgitem)
        ImageView img;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
