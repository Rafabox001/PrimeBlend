package com.manguitostudios.primeblend.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.Utils.Constants;
import com.manguitostudios.primeblend.adapters.ProductAdapter;
import com.manguitostudios.primeblend.network.RegisterCalls;
import com.manguitostudios.primeblend.objects.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
public class ProductsFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.recyclerView)RecyclerView listContainer;
    @Bind(R.id.origin)TextView origen;
    @Bind(R.id.choice)ImageView seleccion;

    private int position = 0;
    private String mCategory;
    private String mOrigin;
    private String mSucategory;
    private ProductAdapter adapter;
    ArrayList<Product> products;

    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_SUBCATEGORY = "subcategory";
    public static final String PARAM_SUBCATEGORY_ID = "subcategory_id";
    private String PARAM_USER = "user_id";
    private String mUserId;

    public ProductsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogo_list, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mCategory = bundle.getString(PARAM_BRAND);
            mOrigin = bundle.getString(PARAM_ORIGIN);
            origen.setText(mOrigin);
            mSucategory = bundle.getString(PARAM_SUBCATEGORY_ID);
            mUserId = bundle.getString(PARAM_USER);
        }
        if (mOrigin.contentEquals("catálogo")){
            ((CatalogoActivity)getActivity()).updateFragment(CatalogoActivity.TAG_CATALOGO_PRODUCTS);
        }else{
            ((CotizadorActivity)getActivity()).updateFragment(CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
        }
        updateUi(mCategory);

        RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.productsRequest);
        registerCalls.execute(mSucategory);

        Glide.with(getActivity())
                .load(R.drawable.background9)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listContainer.setHasFixedSize(true);
        listContainer.setLayoutManager(llm);



        return rootView;
    }

    private void updateUi(String category){
        switch (category){
            case "monogram":
                Glide.with(getActivity())
                        .load(R.drawable.monogram)
                        .into(seleccion);
                break;
            case "iomabe":
                Glide.with(getActivity())
                        .load(R.drawable.iomabe)
                        .into(seleccion);
                break;
            case "profile":
                Glide.with(getActivity())
                        .load(R.drawable.profile)
                        .into(seleccion);
                break;
        }
    }

    public void processOutput(JSONObject response){

        try {
            products = getProductData(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ProductAdapter(getActivity(),products, mCategory, mOrigin, mUserId);

        listContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Product> getProductData(JSONObject object) throws JSONException {

        final String RESULT_CODE = "code";
        final String RESULT_ARRAY = "data";
        final String PRODUCT_ID = "product_id";
        final String PRODUCT_NAME = "name";
        final String PRODUCT_DESCRIPTION = "descripcion";
        final String PRODUCT_PRICE = "price";
        final String PRODUCT_CURRENCY = "currency";
        final String PRODUCT_PDF = "pdf";
        final String PRODUCT_IMAGE = "image";
        final String PRODUCT_THUMBNAIL = "thumbnail";

        //if the width of the screen is bigger than 1000px will set w500

        ArrayList<Product> content;

        if (object.getString(RESULT_CODE).contentEquals("1")){
            try {
                JSONArray productsArray = object.getJSONArray(RESULT_ARRAY);
                Log.d(CatalogoActivity.TAG_CATALOGO_PRODUCTS, String.valueOf(productsArray.length()));
                content = new ArrayList<>();

                //Because of memory issues I´m limiting the results to 30, if necesary I will implement a method to retrieve next images
                if (productsArray.length() > 30){
                    for (int i = 0 ; i < 30 ; i++){
                        Product product = new Product();
                        JSONObject obj = productsArray.getJSONObject(i);
                        product.setProduct_id(obj.getString(PRODUCT_ID));
                        product.setName(obj.getString(PRODUCT_NAME));
                        product.setDescription(obj.getString(PRODUCT_DESCRIPTION));
                        product.setPrice(obj.getString(PRODUCT_PRICE));
                        product.setCurrency(obj.getString(PRODUCT_CURRENCY));
                        product.setPdf(obj.getString(PRODUCT_PDF));
                        product.setImage(obj.getString(PRODUCT_IMAGE));
                        product.setThumbnail(obj.getString(PRODUCT_THUMBNAIL));
                        content.add(product);
                    }
                }else {
                    for (int i = 0; i < productsArray.length(); i++) {
                        Product product = new Product();
                        JSONObject obj = productsArray.getJSONObject(i);
                        product.setProduct_id(obj.getString(PRODUCT_ID));
                        product.setName(obj.getString(PRODUCT_NAME));
                        product.setDescription(obj.getString(PRODUCT_DESCRIPTION));
                        product.setPrice(obj.getString(PRODUCT_PRICE));
                        product.setCurrency(obj.getString(PRODUCT_CURRENCY));
                        product.setPdf(obj.getString(PRODUCT_PDF));
                        product.setImage(obj.getString(PRODUCT_IMAGE));
                        product.setThumbnail(obj.getString(PRODUCT_THUMBNAIL));
                        content.add(product);
                    }
                }
                return content;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }

    }
}
