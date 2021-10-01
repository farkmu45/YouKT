package com.example.youkt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Kedua variabel ini digunakan untuk menyediakan pilihan bank beserta dengan no. rekening untuk masing masing bank
    private String[] banks = {"BRI", "BNI", "BSI", "Mandiri"};
    private String[] bankNumbers = {"12398092", "43278923", "43298434", "129742197"};

    TextView txtBank, txtBankNumber, txtTotalPayment, txtAdminCost, txtUktCost;
    TextInputLayout inputFirstName, inputLastName, inputStudentNumber, inputAddress;
    Button btnEstimate, btnConfirmation;
    AutoCompleteTextView dropdown;

    // Ketiga variabel ini digunakan untuk menyimpan data biaya ukt + mencatat sudah berapa kali button ditekan
    private int ukt;
    private int adminCost;
    private int step = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBank = findViewById(R.id.textview_bank_name);
        txtUktCost = findViewById(R.id.textview_ukt_cost);
        txtTotalPayment = findViewById(R.id.textview_payment);
        txtAdminCost = findViewById(R.id.textview_admin_cost);
        txtBankNumber = findViewById(R.id.textview_bank_number_value);
        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputStudentNumber = findViewById(R.id.input_student_number);
        inputAddress = findViewById(R.id.input_address);
        dropdown = findViewById(R.id.input_payment_val);
        btnEstimate = findViewById(R.id.button_estimate);
        btnConfirmation = findViewById(R.id.button_pay);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, banks);
        dropdown.setAdapter(adapter);

        btnEstimate.setOnClickListener(view -> {
            String firstName = inputFirstName.getEditText().getText().toString();
            String lastName = inputLastName.getEditText().getText().toString();
            String studentNumber = inputStudentNumber.getEditText().getText().toString();
            String address = inputAddress.getEditText().getText().toString();
            String bank = dropdown.getEditableText().toString();

            //  Disini dicek, apakah semua data telah diisi atau belum
            if (firstName.isEmpty() || lastName.isEmpty() || studentNumber.isEmpty() || address.isEmpty() || bank.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            } else {
                calculateUKT(studentNumber);
                setInformation();
                btnConfirmation.setEnabled(true);
            }
        });

        btnConfirmation.setOnClickListener(view -> {
            if (step == 0) {
                Toast.makeText(this, "Silahkan lakukan pembayaran berdasarkan info yang sudah ditampilkan", Toast.LENGTH_SHORT).show();
                btnEstimate.setEnabled(false);
                btnConfirmation.setText("Ulang");
                step++;
            } else if (step == 1) {
                reset();
            }
        });
    }

    // Method ini digunakan untuk mengulang dari awal
    private void reset() {
        step = 0;
        txtBank.setText("");
        txtAdminCost.setText("");
        txtUktCost.setText("");
        txtTotalPayment.setText("");
        txtBankNumber.setText("");

        inputFirstName.getEditText().setText("");
        dropdown.setText("");
        inputAddress.getEditText().setText("");
        inputFirstName.getEditText().setText("");
        inputLastName.getEditText().setText("");
        inputStudentNumber.getEditText().setText("");

        btnConfirmation.setText("Konfirmasi");
        btnEstimate.setEnabled(true);

        inputFirstName.requestFocus();
    }

    // Method ini digunakan untuk mengubah data ukt berdasarkan input yang sudah dimasukkan oleh pengguna
    private void setInformation() {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        int bankNumberIndex = Arrays.asList(banks).indexOf(dropdown.getEditableText().toString());
        txtBank.setText(dropdown.getEditableText().toString());
        txtAdminCost.setText(formatRupiah.format(adminCost));
        txtUktCost.setText(formatRupiah.format(ukt));
        txtTotalPayment.setText(formatRupiah.format(adminCost + ukt));
        txtBankNumber.setText(bankNumbers[bankNumberIndex]);

    }

    // Method ini digunakan untuk menentukan ukt
    private void calculateUKT(String nim) {
        if (nim.startsWith("4")) {
            ukt = 600000;
            adminCost = 500;
        } else if (nim.startsWith("3")) {
            ukt = 12000000;
            adminCost = 600;
        } else {
            ukt = 3000000;
            adminCost = 400;
        }
    }
}