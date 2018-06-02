package com.dvoroncov.furniturecataloguear.data.repository;

import android.support.annotation.NonNull;

import com.dvoroncov.furniturecataloguear.base.BaseRepository;
import com.dvoroncov.furniturecataloguear.base.DataLoadedListener;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryImpl implements BaseRepository, ValueEventListener {

    private DatabaseReference databaseReference;
    private DataLoadedListener dataLoadedListener;
    private Map<Category, List<Furniture>> categories = new HashMap<>();

    public RepositoryImpl() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("categories");
    }

    @Override
    public void loadData(DataLoadedListener dataLoadedListener) {
        databaseReference.addValueEventListener(this);
        this.dataLoadedListener = dataLoadedListener;
    }

    @Override
    public boolean dataLoaded() {
        return !categories.isEmpty();
    }

    @Override
    public List<Category> getCategories() {
        if (categories.isEmpty()){
            return null;
        }
        List<Category> categoryList = new ArrayList<>();
        if (categoryList.addAll(categories.keySet())) {
            return categoryList;
        }
        return null;
    }

    @Override
    public List<Furniture> getFurnitureList() {
        return null;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        categories.clear();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            Category category = child.getValue(Category.class);
            List<Furniture> furnitureList = new ArrayList<>();
            if (child.hasChild("furniture")) {
                for (DataSnapshot furnitureChild : child.child("furniture").getChildren()) {
                    Furniture furniture = furnitureChild.getValue(Furniture.class);
                    furnitureList.add(furniture);
                }
            }
            if (category != null && !furnitureList.isEmpty()) {
                categories.put(category, furnitureList);
            }
        }
        if (dataLoadedListener != null) {
            dataLoadedListener.dataLoaded(!categories.isEmpty());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        databaseError.toException().printStackTrace();
        dataLoadedListener.dataLoaded(false);
    }
}
