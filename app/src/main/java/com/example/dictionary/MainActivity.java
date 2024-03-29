package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.Adapters.MeaningAdapter;
import com.example.dictionary.Adapters.PhoneticsAdapter;
import com.example.dictionary.Models.ApiResponse;

public class MainActivity extends AppCompatActivity {
    SearchView search_view;
    TextView textView_word, textView_origin;
    RecyclerView recycler_phonetics, recycler_meanings;
    ProgressDialog progressDialog;
    PhoneticsAdapter phoneticsAdapter;
    MeaningAdapter meaningAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_view = findViewById(R.id.search_view);
        textView_word = findViewById(R.id.textView_word);
        textView_origin = findViewById(R.id.textView_origin);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading...");
        progressDialog.show();
        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getWordMeaning(listener, "World");

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Getting response for " + query);
                progressDialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeaning(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private final OnFetchDetaListener listener = new OnFetchDetaListener() {
        @Override
        public void onFetchData(ApiResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse==null){
                Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(ApiResponse apiResponse) {
        textView_word.setText("Word "+ apiResponse.getWord());
        textView_origin.setText("Origin " +apiResponse.getOrigin());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this,1));
        phoneticsAdapter =new PhoneticsAdapter(this,apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(phoneticsAdapter);

        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this,1));
        meaningAdapter = new MeaningAdapter(this,apiResponse.getMeanings());
        recycler_meanings.setAdapter(meaningAdapter);
    }
}