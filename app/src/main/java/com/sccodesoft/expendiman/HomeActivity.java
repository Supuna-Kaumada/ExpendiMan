package com.sccodesoft.expendiman;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sccodesoft.expendiman.Adapter.MyAdapter;
import com.sccodesoft.expendiman.Model.LogItem;
import com.sccodesoft.expendiman.Sql.DBHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Integer userId=-1;
    String uincome,uexpense,user_Email;
    SharedPreferences prefs;
    TextView income,expense,balance;

    RecyclerView rv;
    ArrayList<LogItem> al=new ArrayList<>();
    String id,name,amount,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userId = prefs.getInt("user_Id", -1);
        uincome = prefs.getString("user_Income","0");
        uexpense = prefs.getString("user_Expense","0");
        user_Email = prefs.getString("user_Email",null);

        income = (TextView)findViewById(R.id.txtuincome);
        expense = (TextView)findViewById(R.id.txtuexpense);
        balance = (TextView)findViewById(R.id.txtubalance);

        setTextVal();



        com.github.clans.fab.FloatingActionButton fabExpense = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabExpense);
        com.github.clans.fab.FloatingActionButton fabSaving = (FloatingActionButton) findViewById(R.id.fabSaving);
        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem(view, "Expenditure");
            }
        });
        fabSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addListItem(view, "Income");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setTextVal() {
        income.setText(uincome);
        expense.setText(uexpense);
        balance.setText(String.valueOf(Double.valueOf(uincome)-Double.valueOf(uexpense)));

        rv = findViewById(R.id.rev);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        rv.setLayoutManager(layoutManager);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.query("Logs", null, "user_Email=?", new String[]{user_Email}, null, null, null);
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    id = cursor.getString(0);
                    name = cursor.getString(1);
                    amount = cursor.getString(2);
                    type = cursor.getString(4);

                    LogItem logItem = new LogItem(id,name,amount,type);
                    al.add(logItem);

                }while (cursor.moveToNext());
            }
        }

        MyAdapter my = new MyAdapter(HomeActivity.this,al);
        rv.setAdapter(my);
    }

    public void addListItem(View view, final String type) {
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.close(true);
        LinearLayout layout = new LinearLayout(HomeActivity.this);
        TextInputLayout name = new TextInputLayout(HomeActivity.this);
        TextInputLayout value = new TextInputLayout(HomeActivity.this);
        final EditText editTextValue = new EditText(HomeActivity.this);
        final EditText editTextName = new EditText(HomeActivity.this);
        // opens keyboard
        final InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        editTextName.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        // disable non integer inputs and uses numeric keyboard
        editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextName.setMaxLines(1);
        editTextValue.setMaxLines(1);
        editTextName.setHint("Enter name of item");
        editTextValue.setHint("Enter amount");
        // set the enter button to be done
        editTextValue.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // sets the min length of the field
        editTextName.setMinEms(10);
        editTextValue.setMinEms(10);
        // adds edit text objects to a container for floating label
        name.addView(editTextName);
        value.addView(editTextValue);
        layout.addView(name);
        layout.addView(value);
        layout.setPadding(10, 50, 10, 10);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder .setView(layout)
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String stringName = editTextName.getText().toString();
                        String stringValue = editTextValue.getText().toString();
                        if (stringName.isEmpty()) {
                            // alert the user with a toast message if the name field is empty
                            Toast.makeText(HomeActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();

                        }
                        else if (stringValue.isEmpty()) {
                            // alert the user with a toast message if the number field is empty
                            Toast.makeText(HomeActivity.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DBHelper helper = new DBHelper(getBaseContext());
                            SQLiteDatabase db = helper.getReadableDatabase();
                            ContentValues updateValues = new ContentValues();
                            updateValues.put("Amount", stringValue);
                            updateValues.put("Name", stringName);
                            updateValues.put("Type",type);
                            updateValues.put("User_Email",user_Email);
                            db.insert("Logs",null, updateValues);

                            ContentValues updateValues1 = new ContentValues();
                            if(type.equals("Income")) {
                                uincome = String.valueOf(Double.valueOf(uincome) + Double.valueOf(stringValue));
                                updateValues1.put(type, uincome);
                                prefs.edit().putString("user_Income", String.valueOf(Double.valueOf(uincome) + Double.valueOf(stringValue))).commit();
                            }
                            if(type.equals("Expenditure")) {
                                uexpense = String.valueOf(Double.valueOf(uexpense) + Double.valueOf(stringValue));
                                updateValues1.put(type, uexpense);
                                prefs.edit().putString("user_Expense", String.valueOf(Double.valueOf(uexpense) + Double.valueOf(stringValue))).commit();
                            }
                            db.update("Users",updateValues1,"User_id=?", new String[]{userId.toString()});
                            setTextVal();
                            Toast.makeText(HomeActivity.this, stringName + " was added to your list.", Toast.LENGTH_SHORT).show();
                        }
                        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
