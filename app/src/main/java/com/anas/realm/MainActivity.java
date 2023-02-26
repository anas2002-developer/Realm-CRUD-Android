package com.anas.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText eStudent_name, eStudent_uid, eStudent_serial;
    Button btnInsert,btnPrint,btnUpdate,btnDelete;


    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eStudent_serial = findViewById(R.id.eStudent_serial);
        eStudent_name = findViewById(R.id.eStudent_name);
        eStudent_uid = findViewById(R.id.eStudent_uid);
        btnInsert = findViewById(R.id.btnInsert);
        btnPrint = findViewById(R.id.btnPrint);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);

        btnInsert.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnInsert:
                Insert();
                break;
            case R.id.btnPrint:
                Print();
                break;
            case R.id.btnUpdate:
                Update();
                break;
            case R.id.btnDelete:
                Delete();
                break;
        }
    }




    public void Insert() {

        String name, uid, serial;
        serial = eStudent_serial.getText().toString();
        name = eStudent_name.getText().toString();
        uid = eStudent_uid.getText().toString();

//        try {
//            realm.beginTransaction();
//
//            Model_Students model = realm.createObject(Model_Students.class, Integer.parseInt(serial));
//            model.setStudent_name(name);
//            model.setStudent_uid(uid);
//            realm.commitTransaction();
//
//            Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            realm.commitTransaction();
//            Toast.makeText(this, "Not Inserted", Toast.LENGTH_SHORT).show();
//        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

//                Model_Students model = realm.createObject(Model_Students.class, Integer.parseInt(serial));
//                model.setStudent_name(name);
//                model.setStudent_uid(uid);

//                realm.copyToRealm(new Model_Students(Integer.parseInt(serial), name, uid));
//
//
                ArrayList<Model_Students> arrStudents=new ArrayList<>();
                arrStudents.add(new Model_Students(Integer.parseInt(serial), name, uid));

                realm.insert(arrStudents);


                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Print() {

//        RealmQuery<Model_Students> query = realm.where(Model_Students.class).between("Student_serial",10,15);             //.equalTo("Student_serial",10);
        RealmQuery<Model_Students> query = realm.where(Model_Students.class).limit(5).sort("Student_serial", Sort.DESCENDING);
        RealmResults<Model_Students> results = query.findAll();
//        Model_Students results = query.findFirst();


        for (int i=0;i<results.size();i++){
            System.out.println(results.get(i).getStudent_serial()+" | "
            +results.get(i).getStudent_name()+" | "+results.get(i).getStudent_uid());
        }
//        System.out.println(results.getStudent_serial()+" | "
//                    +results.getStudent_name()+" | "+results.getStudent_uid());

        Toast.makeText(this, "Print Successful", Toast.LENGTH_SHORT).show();
    }

    private void Update(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String name, uid, serial;
                serial = eStudent_serial.getText().toString();
                name = eStudent_name.getText().toString();
                uid = eStudent_uid.getText().toString();

                Model_Students model = realm.where(Model_Students.class).equalTo("Student_serial",Integer.parseInt(serial)).findFirst();
                model.setStudent_name(name);
                model.setStudent_uid(uid);


                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Delete() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String name, uid, serial;
                serial = eStudent_serial.getText().toString();
                name = eStudent_name.getText().toString();
                uid = eStudent_uid.getText().toString();

//                Model_Students model = realm.where(Model_Students.class).equalTo("Student_serial",Integer.parseInt(serial)).findFirst();
//                model.deleteFromRealm();

                RealmResults<Model_Students> model = realm.where(Model_Students.class).between("Student_serial",3,5).findAll();
                model.deleteAllFromRealm();


                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}