package com.manguitostudios.primeblend.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.MainActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.adapters.CheckoutAdapter;
import com.manguitostudios.primeblend.adapters.ProductAdapter;
import com.manguitostudios.primeblend.network.RegisterCalls;
import com.manguitostudios.primeblend.objects.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manguitodeveloper01 on 10/14/15.
 */
public class CheckoutFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.recyclerView)RecyclerView listContainer;
    @Bind(R.id.descuento)EditText descuento;
    @Bind(R.id.total)TextView total;
    @Bind(R.id.tipo_cambio)TextView tipoCambio;
    @Bind(R.id.date)TextView date;
    @Bind(R.id.list)LinearLayout checkoutList;
    @Bind(R.id.sellerData)LinearLayout sellerInfo;
    @Bind(R.id.sellerName)EditText nombreVendedor;
    @Bind(R.id.sellerEmail)EditText emailVendedor;

    private String mCategory;
    private String mOrigin;
    private Product mProduct;
    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_PRODUCT = "product";
    public static final String PARAM_CANTIDAD = "cantidad";

    public static final String JSON_PRODUCT_ID = "product_id";
    public static final String JSON_PRODUCT_QTY = "qty";
    public static final String JSON_PRODUCTS = "products";
    public static final String JSON_USER = "user_id";
    public static final String JSON_PRODUCT_PRICE = "price";
    public static final String JSON_PRODUCT_THUMBNAIL = "thumbnail";
    public static final String JSON_PRODUCT_NAME = "name";
    public static final String JSON_PRODUCT_DISCOUNT = "discount";
    public static final String JSON_SELLER_NAME = "seller_name";
    public static final String JSON_SELLER_EMAIL = "seller_email";


    public Boolean detail = false;


    public static final String PREF_DATA = "json_data";
    public static final String PREF_SELLER_NAME = "seller_name";
    public static final String PREF_SELLER_EMAIL = "seller_email";
    private String mData;
    public static final String PREF_LIST = "json_list";
    private String mList;
    private double price;
    private double exchangeRate;
    private double productPrice;

    private String codeResponse;
    private String quoteCode;
    private String mUserId;


    private String PARAM_CODE = "code";
    private String PARAM_USER = "user_id";
    private String PARAM_QUOTE = "quote_id";
    private String PARAM_EXCHANGE ="exchange_rate";

    private CheckoutAdapter adapter;
    ArrayList<Product> products;

    public CheckoutFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cotizador_last, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mCategory = bundle.getString(PARAM_BRAND);
            mOrigin = bundle.getString(PARAM_ORIGIN);
            mProduct = bundle.getParcelable(PARAM_PRODUCT);
            mUserId = bundle.getString(PARAM_USER);

        }
        if (mOrigin.contentEquals("catálogo")){
            ((CatalogoActivity)getActivity()).updateFragment(CatalogoActivity.TAG_CATALOGO_CHECKOUT);
        }else{
            ((CotizadorActivity)getActivity()).updateFragment(CotizadorActivity.TAG_COTIZADOR_CHECKOUT);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.contains(PREF_DATA)){
            mData = prefs.getString(PREF_DATA, null);
            mList = prefs.getString(PREF_LIST, null);
            nombreVendedor.setText(prefs.getString(PREF_SELLER_NAME, ""));
            emailVendedor.setText(prefs.getString(PREF_SELLER_EMAIL, ""));
        }else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PREF_DATA, mData); // value to store
            editor.putString(PREF_LIST, mList);
            editor.putString(PREF_SELLER_NAME, ""); // value to store
            editor.putString(PREF_SELLER_EMAIL, "");
            editor.commit();
        }
        updateJsonData(mData);
        updateListData(mList);

        RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.exchangeRateRequest);
        registerCalls.execute();



        Glide.with(getActivity())
                .load(R.drawable.background)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listContainer.setHasFixedSize(true);
        listContainer.setLayoutManager(llm);

        descuento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contentEquals("")){
                    if (Integer.valueOf(descuento.getText().toString())<10){
                        Double newPrice = price - (price * (Double.valueOf(".0"+descuento.getText().toString())));
                        String updatedPrice = String.format("%.2f", newPrice);
                        total.setText(getString(R.string.total_cotizacion, updatedPrice));
                    }else{
                        Double newPrice = price - (price * (Double.valueOf("."+descuento.getText().toString())));
                        String updatedPrice = String.format("%.2f", newPrice);
                        total.setText(getString(R.string.total_cotizacion, updatedPrice));
                    }

                }

            }
        });

        return rootView;
    }

    private void updateJsonData(String mData){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mData !=null && mProduct != null){
            try{
                JSONObject baseObject = new JSONObject(mData);
                JSONArray productsArray = baseObject.getJSONArray(JSON_PRODUCTS);
                JSONObject productObject = new JSONObject();
                productObject.put(JSON_PRODUCT_ID, mProduct.getProduct_id());
                productObject.put(JSON_PRODUCT_QTY, mProduct.getCantidad());
                productsArray.put(productObject);
                mData = baseObject.toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_DATA, mData); // value to store
                editor.commit();
                Log.d("JSON_EXIST", baseObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (mData ==null && mProduct != null){
            JSONObject baseObject = new JSONObject();
            JSONArray productsArray = new JSONArray();
            JSONObject productObject = new JSONObject();
            try {
                productObject.put(JSON_PRODUCT_ID, mProduct.getProduct_id());
                productObject.put(JSON_PRODUCT_QTY, mProduct.getCantidad());
                productsArray.put(productObject);
                baseObject.put(JSON_PRODUCTS, productsArray);
                baseObject.put(JSON_USER,mUserId);
                mData = baseObject.toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_DATA, mData); // value to store
                editor.commit();
                Log.d("JSON_NEW", baseObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void updateListData(String mList){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mList !=null && mProduct != null){
            try{
                JSONObject baseObject = new JSONObject(mList);
                JSONArray productsArray = baseObject.getJSONArray(JSON_PRODUCTS);
                JSONObject productObject = new JSONObject();
                productObject.put(JSON_PRODUCT_ID, mProduct.getProduct_id());
                productObject.put(JSON_PRODUCT_QTY, mProduct.getCantidad());
                productObject.put(JSON_PRODUCT_PRICE, mProduct.getPrice());
                productObject.put(JSON_PRODUCT_THUMBNAIL, mProduct.getThumbnail());
                productObject.put(JSON_PRODUCT_NAME, mProduct.getName());
                productsArray.put(productObject);
                mList = baseObject.toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_LIST, mList); // value to store
                editor.commit();
                Log.d("LIST_EXIST", baseObject.toString());
                try {
                    products = getProductData(baseObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (mList !=null && mProduct == null){
            try{
                JSONObject baseObject = new JSONObject(mList);
                JSONArray productsArray = baseObject.getJSONArray(JSON_PRODUCTS);
                mList = baseObject.toString();

                try {
                    products = getProductData(baseObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (mProduct != null){
            JSONObject baseObject = new JSONObject();
            JSONArray productsArray = new JSONArray();
            JSONObject productObject = new JSONObject();
            try {
                productObject.put(JSON_PRODUCT_ID, mProduct.getProduct_id());
                productObject.put(JSON_PRODUCT_QTY, mProduct.getCantidad());
                productObject.put(JSON_PRODUCT_PRICE, mProduct.getPrice());
                productObject.put(JSON_PRODUCT_THUMBNAIL, mProduct.getThumbnail());
                productObject.put(JSON_PRODUCT_NAME, mProduct.getName());
                productsArray.put(productObject);
                baseObject.put(JSON_PRODUCTS, productsArray);
                baseObject.put(JSON_USER,mUserId);
                mList = baseObject.toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_LIST, mList); // value to store
                editor.commit();
                Log.d("LIST_NEW", baseObject.toString());
                try {
                    products = getProductData(baseObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        updateTotal(products);
        adapter = new CheckoutAdapter(getActivity(),products);

        listContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void updateTotal(ArrayList<Product> products) {
        price = 0;
        for(int i = 0; i < products.size(); i++){
            if (!products.get(i).getPrice().contentEquals("")){
                productPrice = Double.valueOf(products.get(i).getPrice()) * products.get(i).getCantidad();
                price = price + productPrice;
            }
        }
        String s = String.format("%.2f", price);
        total.setText(getString(R.string.total_cotizacion, s));
    }

    public ArrayList<Product> getProductData(JSONObject object) throws JSONException {

        final String RESULT_CODE = "code";
        final String RESULT_ARRAY = "products";
        final String PRODUCT_ID = "product_id";
        final String PRODUCT_NAME = "name";
        final String PRODUCT_QTY = "qty";
        final String PRODUCT_PRICE = "price";
        final String PRODUCT_THUMBNAIL = "thumbnail";

        //if the width of the screen is bigger than 1000px will set w500

        ArrayList<Product> content;

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
                    product.setPrice(obj.getString(PRODUCT_PRICE));
                    product.setCantidad(obj.getInt(PRODUCT_QTY));
                    product.setThumbnail(obj.getString(PRODUCT_THUMBNAIL));
                    content.add(product);
                }
            }else {
                for (int i = 0; i < productsArray.length(); i++) {
                    Product product = new Product();
                    JSONObject obj = productsArray.getJSONObject(i);
                    product.setProduct_id(obj.getString(PRODUCT_ID));
                    product.setName(obj.getString(PRODUCT_NAME));
                    product.setPrice(obj.getString(PRODUCT_PRICE));
                    product.setCantidad(obj.getInt(PRODUCT_QTY));
                    product.setThumbnail(obj.getString(PRODUCT_THUMBNAIL));
                    content.add(product);
                }
            }
            return content;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public void processOutput(JSONObject response){
        Log.d("CHECK_RESPONSE", response.toString());
        if (response.toString().contains("exchange")){
            try {
                codeResponse = response.getString(PARAM_CODE);
                exchangeRate = response.getDouble(PARAM_EXCHANGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                codeResponse = response.getString(PARAM_CODE);
                quoteCode = response.getString(PARAM_QUOTE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (codeResponse.contentEquals("0")){
            showDialog(getString(R.string.error_checkout));

        }else if (codeResponse.contentEquals("1")){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PREF_LIST, null); // value to store
            editor.putString(PREF_DATA, null); // value to store
            editor.commit();

            showConfirmationDialog(getString(R.string.confirmation));

        }else if (codeResponse.contentEquals("2")){
            String s = String.format("%.2f", exchangeRate);
            tipoCambio.setText(getString(R.string.tipo_cambio, s));
        }


    }

    public void showDialog (String message){
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showConfirmationDialog (String message){
        new AlertDialog.Builder(getActivity())
                .setTitle("Envio exitoso!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent i  = new Intent(getActivity(), MainActivity.class);
                        i.putExtra(PARAM_USER, mUserId);
                        startActivity(i);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    @OnClick(R.id.viewProducts)
    public void navigateCatalog(){
        Intent i  = new Intent(getActivity(), MainActivity.class);
        i.putExtra(PARAM_USER, mUserId);
        startActivity(i);
    }

    @OnClick(R.id.sendMail)
    public void sendMail(){
        checkoutList.setVisibility(View.GONE);
        sellerInfo.setVisibility(View.VISIBLE);
        detail = true;
    }

    public void navigateBack(){
        checkoutList.setVisibility(View.VISIBLE);
        sellerInfo.setVisibility(View.GONE);
        detail = false;
    }

    @OnClick(R.id.send)
    public void sendValuation(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json = "";
        if (mData !=null) {
            try {
                JSONObject baseObject = new JSONObject(mData);
                baseObject.put(JSON_PRODUCT_DISCOUNT, descuento.getText().toString());
                baseObject.put(JSON_SELLER_NAME, nombreVendedor.getText().toString());
                baseObject.put(JSON_SELLER_EMAIL, emailVendedor.getText().toString());
                json = baseObject.toString();
                Log.d("SERVICE_JSON", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_SELLER_NAME, nombreVendedor.getText().toString()); // value to store
        editor.putString(PREF_SELLER_EMAIL, emailVendedor.getText().toString()); // value to store
        editor.commit();


            RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.valuationRequest);
        registerCalls.execute(json, mUserId);
    }
}
