package acg.edu.taliouris.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import acg.edu.taliouris.R;
import acg.edu.taliouris.model.Movie;

public class CreateActivity extends AppCompatActivity {
    private Spinner type;
    private EditText title,director,dor;
    private TextInputLayout titleLayout,directorLayout,dorLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        type = findViewById(R.id.type);
        title = findViewById(R.id.title);
        director = findViewById(R.id.director);
        dor = findViewById(R.id.dor);
        titleLayout = findViewById(R.id.movie_container);
        directorLayout = findViewById(R.id.director_container);
        dorLayout = findViewById(R.id.dor_container);
        Button save = findViewById(R.id.save);


        // Populate the type spinner
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.types));
        ArrayAdapter<String>typesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
        }

    };
    typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    type.setAdapter(typesAdapter);


    // Check form and, if OK, save movie

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationFailed = false;
                SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
                Date date = null;
                try {
                    date = sdf.parse(dor.getText().toString());
                } catch (ParseException e) {
                    dorLayout.setError(getString(R.string.dor_error));
                    dorLayout.requestFocus();
                    validationFailed = true;
                }
                String directorStr = director.getText().toString();
                if (directorStr.isEmpty()) {
                    directorLayout.setError(getString(R.string.director_error));
                    directorLayout.requestFocus();
                    validationFailed = true;
                }
                String titleStr = title.getText().toString();
                if (titleStr.isEmpty()) {
                    titleLayout.setError(getString(R.string.title_error));
                    titleLayout.requestFocus();
                    validationFailed = true;
                }
                if (validationFailed) {
                    return;
                }
                String typeStr = type.getSelectedItem().toString();
                if (type.getSelectedItemPosition() == 0) {
                    typeStr = getString(R.string.unknown_type);
                }
                Movie movie = new Movie(titleStr, directorStr,typeStr,date);
                Intent intent = new Intent();
                intent.putExtra("movie", movie.serialize());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Remove errors when user starts typing in edit texts
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                titleLayout.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        director.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                directorLayout.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        dor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                dorLayout.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }





}