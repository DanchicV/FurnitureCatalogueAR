package com.dvoroncov.furniturecataloguear.ui.furniture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.ViewHolder> {

    private List<Furniture> furnitureList = new ArrayList<>();
    private FurnitureSelectedListener furnitureSelectedListener;

    public FurnitureAdapter(FurnitureSelectedListener furnitureSelectedListener) {
        this.furnitureSelectedListener = furnitureSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_furniture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(furnitureList.get(position), furnitureSelectedListener);
    }

    public void setCategories(List<Furniture> furnitureList) {
        this.furnitureList = furnitureList;
    }

    @Override
    public int getItemCount() {
        return furnitureList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name_text_view)
        TextView categoryNameTextView;

        @BindView(R.id.furniture_image)
        ImageView furnitureImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void onBind(final Furniture furniture, final FurnitureSelectedListener furnitureSelectedListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    furnitureSelectedListener.itemSelected(furniture);
                }
            });

            Picasso.get().load(furniture.getImage()).into(furnitureImage);
            categoryNameTextView.setText(furniture.getName());
        }
    }

    public interface FurnitureSelectedListener {

        void itemSelected(Furniture furniture);
    }
}
