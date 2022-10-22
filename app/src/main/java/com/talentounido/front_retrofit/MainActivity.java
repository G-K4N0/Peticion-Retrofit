package com.talentounido.front_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private EditText txtId, txtFullName, txtName,txtAddress, txtPhone, txtAge;

    private ListView listData;
     ArrayAdapter adapter;
    private ArrayList<String> dataList;
    private Button btnCreate,btnUpdate, btnDelete;
    private String baseURL = "http://192.168.2.33:3000/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listData = findViewById(R.id.lstData);
        initialize();
        buttonCreate();
        getUsers();
        updateUser();
        deleteUser();
        clickItem();
    }

    private void clear()
    {
        txtId.setText("");
        txtFullName.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtAge.setText("");
    }
    private void initialize(){
        txtId = findViewById(R.id.txtId);
        txtFullName = findViewById(R.id.txtFullName);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        txtAge = findViewById(R.id.txtAge);

        btnCreate = findViewById(R.id.btnCreate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void buttonCreate()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);

        btnCreate.setOnClickListener(v -> {

            String fullName, name, address, phone;
            int age;

            fullName = txtFullName.getText().toString();
            name = txtName.getText().toString();
            address = txtAddress.getText().toString();
            phone = txtPhone.getText().toString();
            age = Integer.parseInt(txtAge.getText().toString());

            Person person = new Person(fullName,name,address,phone,age);
            Call<Person> call = service.createPerson(person);

            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                    clear();
                    getUsers();
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error ->" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void getUsers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);

        Call<List<Person>> call= service.getPersons();

        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "->" + response.code(), Toast.LENGTH_SHORT).show();
                }

                List<Person> personList = response.body();
                dataList = new ArrayList<>();
                for(Person person:personList)
                {
                    String data = "";

                    data += "id:" + person.get_id()+
                            ",fullname:" + person.getFullName()+
                            ",name:" + person.getName() +
                            ",addres:" + person.getAddress() +
                            ",phone:" + person.getPhone() +
                            ",age:" + person.getAge();

                    dataList.add(data);
                }
                adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1, dataList);
                listData.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private  void clickItem()
    {
        listData.setOnItemClickListener((parent, view, position, id) -> {
            String [] data = dataList.get(position).split(":");

            String []_id = data[1].split(",");
            txtId.setText(_id[0]);

            _id = data[2].split(",");
            txtFullName.setText(_id[0]);

            _id = data[3].split(",");
            txtName.setText(_id[0]);

            _id = data[4].split(",");
            txtAddress.setText(_id[0]);

            _id = data[5].split(",");
            txtPhone.setText(_id[0]);

            txtAge.setText(data[6]);
        });
    }
    private void updateUser()
    {
        btnUpdate.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DataService service = retrofit.create(DataService.class);

            String id = txtId.getText().toString();

            String fullName, name, address, phone;
            int age;

            fullName = txtFullName.getText().toString();
            name = txtName.getText().toString();
            address = txtAddress.getText().toString();
            phone = txtPhone.getText().toString();
            age = Integer.parseInt(txtAge.getText().toString());

            Person person = new Person(fullName,name,address,phone,age);
            Call<Person> call = service.putUser(id,person);

            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, id + " -> Editado", Toast.LENGTH_SHORT).show();
                    clear();
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error Update -> " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
    private void deleteUser(){
        btnDelete.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DataService service = retrofit.create(DataService.class);

            String id = txtId.getText().toString();
            String name = txtName.getText().toString();
            Call<Person> call= service.deleteUser(id);

            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "User "+ name + " deleted" , Toast.LENGTH_SHORT).show();
                    getUsers();
                    clear();
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error -> " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}