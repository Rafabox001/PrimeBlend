package com.manguitostudios.primeblend.adapters;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.manguitostudios.primeblend.fragments.ProductsFragment;
import com.manguitostudios.primeblend.listeners.onImageSelectedListener;
import com.manguitostudios.primeblend.objects.Product;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Product> products;
    private onImageSelectedListener listener;
    private boolean isFirstTime;
    private String mCategory;
    private String mOrigin;

    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_PRODUCT = "product";

    public ProductAdapter(Context context, ArrayList<Product> all_products, String category, String origin) {
        mContext = context;
        products = all_products;
        isFirstTime = true;
        mCategory = category;
        mOrigin = origin;
    }
    public ProductAdapter(Context context, ArrayList<Product> all_products, onImageSelectedListener listener) {
        mContext = context;
        products = all_products;
        this.listener = listener;
        isFirstTime = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.product_item, null, false);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(products.get(position).getName());
        viewHolder.description.setText(products.get(position).getDescription());
        Glide.with(mContext).load(products.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.image);
        final int pos = position;
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(products.get(position));
            }
        });
        //viewHolder.description.setText(products.get(position).getTitle());

    }

    private void navigate(Product product){
        ProductDetailFragment mFragment = new ProductDetailFragment();


        Bundle args = new Bundle();
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_BRAND, mCategory);
        args.putParcelable(PARAM_PRODUCT, product);

        mFragment.setArguments(args);
        if (mOrigin.contentEquals("catálogo")){
            switchContent(R.id.fragment_catalogo_container, mFragment);
        }else{
            switchContent(R.id.fragment_cotizador_container, mFragment);
        }

    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof CatalogoActivity) {
            CatalogoActivity catalogoActivity = (CatalogoActivity) mContext;
            Fragment frag = fragment;
            catalogoActivity.switchContent(id, frag);
        }else if (mContext instanceof CotizadorActivity) {
            CotizadorActivity cotizadorActivity = (CotizadorActivity) mContext;
            Fragment frag = fragment;
            cotizadorActivity.switchContent(id, frag);
        }

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
        @Bind(R.id.productName) TextView name;
        @Bind(R.id.productDescription) TextView description;
        @Bind(R.id.container)RelativeLayout container;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
