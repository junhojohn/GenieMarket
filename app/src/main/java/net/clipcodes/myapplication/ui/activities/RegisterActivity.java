package net.clipcodes.myapplication.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.models.AdditionalSellerInfo;
import net.clipcodes.myapplication.ui.widgets.ClearEditText;

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
    Spinner spinner;
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

        productInfo.setBigCategory(spinner.getSelectedItem().toString());
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
        spinner = (Spinner)findViewById(R.id.tv_bigCategoryName);
        ArrayAdapter bigCategorySpinnerAdapater = ArrayAdapter.createFromResource(this, R.array.bigCategorySpinner, android.R.layout.simple_spinner_item);
        bigCategorySpinnerAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(bigCategorySpinnerAdapater);

        editTextProductName.addTextChangedListener(productNameTextWatcher);
        editTextPrice.addTextChangedListener(productPriceTextWatcher);
        editTextProductDescription.addTextChangedListener(productDescriptionTextWatcher);
        numberPicker.setValueChangedListener(numberPickerWatcher);
        spinner.setOnItemSelectedListener(spinnerListener);

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

    public AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            productInfo.setBigCategory(parent.getSelectedItem().toString());

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
