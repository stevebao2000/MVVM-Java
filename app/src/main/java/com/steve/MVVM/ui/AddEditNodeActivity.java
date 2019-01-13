package com.steve.MVVM.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.steve.MVVM.R;

public class AddEditNodeActivity extends AppCompatActivity {
    public static final String EXTRA_ID= "mvvm_EXTRA_ID";
    public static final String EXTRA_NAME = "mvvm_EXTRA_NAME";
    public static final String EXTRA_EMAIL = "mvvm_EXTRA_EMAIL";

    private EditText editName;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Node");
            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
        } else
            setTitle("Add Node");

    }

    private void saveNode() {
        String title = editName.getText().toString();
        String desc = editEmail.getText().toString();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Name or email can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, title);
        data.putExtra(EXTRA_EMAIL, desc);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_node_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_node:
                saveNode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
