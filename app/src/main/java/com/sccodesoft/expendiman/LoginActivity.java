package com.sccodesoft.expendiman;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.sccodesoft.expendiman.Helpers.InputValidation;
import com.sccodesoft.expendiman.Sql.DBHelper;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class LoginActivity extends AppCompatActivity {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;

    SharedPreferences prefs=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();

        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();
            }
        });

        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        prefs = getDefaultSharedPreferences(this);
        inputValidation = new InputValidation(activity);

    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        else if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        else
        {
            DBHelper helper = new DBHelper(getBaseContext());
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor=db.query("Users", null, " Email=?", new String[]{textInputEditTextEmail.getText().toString()}, null, null, null);
            if(cursor.getCount()<1)             {
                Toast.makeText(LoginActivity.this, "Email does not match", Toast.LENGTH_LONG).show();
                cursor.close();
            }else {
                cursor.moveToFirst();
                String storedPassword = cursor.getString(cursor.getColumnIndex("Password"));
                int userId = cursor.getInt(cursor.getColumnIndex("User_id"));
                String uincome = cursor.getString(cursor.getColumnIndex("Income"));
                String uexpense = cursor.getString(cursor.getColumnIndex("Expenditure"));
                String uemail = cursor.getString(cursor.getColumnIndex("Email"));
                cursor.close();
                if (textInputEditTextPassword.getText().toString().equals(storedPassword)) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                    prefs.edit().putInt("user_Id", userId).commit();
                    prefs.edit().putString("user_Email", uemail).commit();
                    prefs.edit().putString("user_Income", uincome).commit();
                    prefs.edit().putString("user_Expense", uexpense).commit();
                    emptyInputEditText();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}