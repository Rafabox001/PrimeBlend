package com.manguitostudios.primeblend.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.fragments.ProductDetailFragment;
import com.manguitostudios.primeblend.listeners.onImageSelectedListener;
import com.manguitostudios.primeblend.objects.Product;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by manguitodeveloper01 on 10/14/15.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Product> products;
    private onImageSelectedListener listener;

    public CheckoutAdapter(Context context, ArrayList<Product> all_products) {
        mContext = context;
        products = all_products;
    }
    public CheckoutAdapter(Context context, ArrayList<Product> all_products, onImageSelectedListener listener) {
        mContext = context;
        products = all_products;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.checkout_item, null, false);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.cantidad.setText(String.valueOf(products.get(position).getCantidad()));
        viewHolder.precio.setText(products.get(position).getPrice());
        Glide.with(mContext).load(products.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.image);

        //viewHolder.description.setText(products.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        try{
            return products.size();
        }catch (NullPointerException e){
            return 0;
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.productImage) ImageView image;
        @Bind(R.id.precio) TextView precio;
        @Bind(R.id.cantidad)TextView cantidad;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
