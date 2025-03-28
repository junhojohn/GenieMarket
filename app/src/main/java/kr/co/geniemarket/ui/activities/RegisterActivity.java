package kr.co.geniemarket.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import kr.co.geniemarket.R;
import kr.co.geniemarket.models.AdditionalProductInfo;
import kr.co.geniemarket.models.AdditionalSellerInfo;
import kr.co.geniemarket.ui.widgets.ClearEditText;

import java.io.Serializable;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnRegistration;
    Button btnBack;
    NumberPicker numberPicker;
    ClearEditText editTextProductName;
    ClearEditText editTextPrice;
    EditText editTextProductDescription;
    TextView textViewSellerName;
    Spinner spinner_BigCategory;
    Spinner spinner_SmallCategory;
    AdditionalProductInfo productInfo;
    AdditionalSellerInfo sellerInfo;


    @Override
    protected void onStart() {
        productInfo = new AdditionalProductInfo();

        productInfo.setSellerName(textViewSellerName.getText().toString());

        if(editTextProductName.getText() != null && editTextProductName.getText().toString() != null && !editTextProductName.getText().toString().isEmpty()){
            productInfo.setName(editTextProductName.getText().toString());
        }

        try{
            productInfo.setPrice(Integer.parseInt(editTextPrice.getText().toString()));
        }catch (Exception e){

        }

        if(editTextProductDescription.getText() != null && editTextProductDescription.getText().toString() != null && !editTextProductDescription.getText().toString().isEmpty()){
            productInfo.setDescription(editTextProductDescription.getText().toString());
        }

        productInfo.setItemCount(numberPicker.getValue());
        String checkMessage = isButtonOkValidation(productInfo);
        if(checkMessage != null && !checkMessage.isEmpty()){
            btnRegistration.setEnabled(false);
        }else{
            btnRegistration.setEnabled(true);
        }

        productInfo.setBigCategory(spinner_BigCategory.getSelectedItem().toString());
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellerInfo = (AdditionalSellerInfo)getIntent().getSerializableExtra("sellerInfo");

        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        setTheme(R.style.AppTheme_Cursor);

        btnRegistration = findViewById(R.id.btn_nextStep);
        btnBack = findViewById(R.id.btn_back);

        btnRegistration.setOnClickListener(clik);
        btnBack.setOnClickListener(clik);

        editTextProductName = findViewById(R.id.tvProductName);
        editTextPrice = findViewById(R.id.tvPriceTag);
        editTextProductDescription = findViewById(R.id.tvDescription);
        numberPicker = findViewById(R.id.number_picker);
        textViewSellerName = findViewById(R.id.tvSellerName);
        textViewSellerName.setText(sellerInfo.getSellerID());
        numberPicker.setDisplayFocusable(true);

        spinner_BigCategory = (Spinner)findViewById(R.id.tv_bigCategoryName);
        ArrayAdapter bigCategorySpinnerAdapater = ArrayAdapter.createFromResource(this, R.array.bigCategorySpinner, android.R.layout.simple_spinner_item);
        bigCategorySpinnerAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_BigCategory.setAdapter(bigCategorySpinnerAdapater);

        spinner_SmallCategory = (Spinner)findViewById(R.id.tv_SmallCategoryName);
        ArrayAdapter smallCategorySpinnerAdapater = ArrayAdapter.createFromResource(this, R.array.smallCategorySpinner, android.R.layout.simple_spinner_item);
        smallCategorySpinnerAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_SmallCategory.setAdapter(smallCategorySpinnerAdapater);

        editTextProductName.addTextChangedListener(productNameTextWatcher);
        editTextPrice.addTextChangedListener(productPriceTextWatcher);
        editTextProductDescription.addTextChangedListener(productDescriptionTextWatcher);
        numberPicker.setValueChangedListener(numberPickerWatcher);
        spinner_BigCategory.setOnItemSelectedListener(bigSpinnerListener);
        spinner_SmallCategory.setOnItemSelectedListener(smallSpinnerListener);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("상품등록");
//        getSupportActionBar().setIcon(R.drawable.ic_back_button_24dp);
    }

    @Override
    protected void onPause() {
        productInfo = null;
        super.onPause();
    }

    public TextWatcher productNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s != null){
                productInfo.setName(s.toString());
                String checkMessage = isButtonOkValidation(productInfo);
                if(checkMessage != null && !checkMessage.isEmpty()){
                    btnRegistration.setEnabled(false);
                }else{
                    btnRegistration.setEnabled(true);
                }
            }else{
                btnRegistration.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher productPriceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s != null){
                try{
                    productInfo.setPrice(Integer.parseInt(s.toString()));
                }catch (Exception e){

                }finally {
                    String checkMessage = isButtonOkValidation(productInfo);
                    if(checkMessage != null && !checkMessage.isEmpty()){
                        btnRegistration.setEnabled(false);
                    }else{
                        btnRegistration.setEnabled(true);
                    }
                }
            }else{
                btnRegistration.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public AdapterView.OnItemSelectedListener bigSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            productInfo.setBigCategory(parent.getSelectedItem().toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public AdapterView.OnItemSelectedListener smallSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            productInfo.setSmallCategory(parent.getSelectedItem().toString());

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_shirt)) ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_pants))    ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_coat))     ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_hat))      ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_socks))    ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_glove))    ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_etc_clothes))){
                productInfo.setMidCategory(getString(R.string.title_category_clothes));
            }

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_high_heels))    ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_walker))           ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_flat_shoes))       ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_sneakers))         ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_slippers))         ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_kids_shoes))       ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_etc_shoes))){
                productInfo.setMidCategory(getString(R.string.title_category_shoes));
            }

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_todback))   ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_wallet))       ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_clutch_bags))  ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_backpack))     ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_coin_wallet))){
                productInfo.setMidCategory(getString(R.string.title_category_merchandise));
            }

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_watch)) ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_bracelet)) ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_etc_accessaries))){
                productInfo.setMidCategory(getString(R.string.title_category_accessaries));
            }

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_doll))  ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_blocks))   ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_etc_toys))) {
                productInfo.setMidCategory(getString(R.string.title_category_toys));
            }

            if(parent.getSelectedItem().toString().equals(getString(R.string.title_category_cellphone_accessaries)) ||
            parent.getSelectedItem().toString().equals(getString(R.string.title_category_etc_electronics))){
                productInfo.setMidCategory(getString(R.string.title_category_electronics));
            }
            System.out.print(productInfo);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    public TextWatcher productDescriptionTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s != null){
                productInfo.setDescription(s.toString());
                String checkMessage = isButtonOkValidation(productInfo);
                if(checkMessage != null && !checkMessage.isEmpty()){
                    btnRegistration.setEnabled(false);
                }else{
                    btnRegistration.setEnabled(true);
                }
            }else{
                btnRegistration.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public ValueChangedListener numberPickerWatcher = new ValueChangedListener() {
        @Override
        public void valueChanged(int value, ActionEnum action) {
            productInfo.setItemCount(value);
            String checkMessage = isButtonOkValidation(productInfo);
            if(checkMessage != null && !checkMessage.isEmpty()){
                btnRegistration.setEnabled(false);
                Toast.makeText(RegisterActivity.this, checkMessage, Toast.LENGTH_SHORT).show();
            }else{
                btnRegistration.setEnabled(true);
            }
        }
    };

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_nextStep:
                    Intent mIntent = new Intent(view.getContext(), ChooseImagesActivity.class);
                    mIntent.putExtra("productInfo", (Serializable)productInfo);
                    view.getContext().startActivity(mIntent);
                    break;
                case R.id.btn_back:
                    finish();
                    break;
            }


        }
    };

    private String isButtonOkValidation(AdditionalProductInfo productInfo){
        if(productInfo == null){
            return "ProducInfo 객체가 없습니다.";
        }

        if(productInfo.getName() == null || productInfo.getName().isEmpty()){
            return "상품명을 입력해주세요.";
        }

        if(productInfo.getPrice() == -1 || productInfo.getPrice() == 0){
            return "상품가격을 입력해주세요.";
        }

        if(productInfo.getDescription() == null || productInfo.getDescription().isEmpty()){
            return "상품 설명을 입력해주세요.";
        }

        if(productInfo.getItemCount() == -1 || productInfo.getItemCount() == 0){
            return "판매 단위를 입력해주세요.";
        }

        return "";
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
