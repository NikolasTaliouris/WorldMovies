package acg.edu.taliouris.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acg.edu.taliouris.R;
import acg.edu.taliouris.adapters.MovieAdapter;
import acg.edu.taliouris.model.Movie;

public class ListActivity extends AppCompatActivity {

    private static final int MOVIE_CREATED = 1;
    private List<Movie> movie;
    private Gson gson;
    private SharedPreferences database;
    private BaseAdapter moviesAdapter;
    private ListView movies;
    private TextView noMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        movies = findViewById(R.id.movie_list);
        noMovies = findViewById(R.id.no_movies);

        // Load list of contacts. Display contacts if not empty, or show message to add first
        movie = new ArrayList<>();
        database = getSharedPreferences("database", Context.MODE_PRIVATE);
        String json = database.getString("movie","");
        gson = new Gson();
        if (!json.isEmpty()){
            movie = new ArrayList<>(Arrays.asList(gson.fromJson(json,Movie[].class)));
            movies.setVisibility(View.VISIBLE);
            noMovies.setVisibility(View.INVISIBLE);
        }
        else{
            movies.setVisibility(View.INVISIBLE);
            noMovies.setVisibility(View.VISIBLE);
        }
        moviesAdapter = new MovieAdapter(this,movie);
        movies.setAdapter(moviesAdapter);

        movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                intent.putExtra("movie", movie.serialize());
                startActivity(intent);
            }
        });

        movies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                movie.remove(position);
                storeAndUpdate();
                return false;
            }
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getBaseContext(), CreateActivity.class), MOVIE_CREATED);
            }
        });

        Button imbd = findViewById(R.id.web_button);
        imbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://www.imdb.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != MOVIE_CREATED || resultCode != RESULT_OK || data == null)
            return;
        movie.add(new Movie(data.getStringExtra("movie")));
        storeAndUpdate();
    }

    private void storeAndUpdate() {
        SharedPreferences.Editor editor = database.edit();
        editor.putString("movie", gson.toJson(movie));
        editor.apply();
        if (movie.size() > 0 && movies.getVisibility() == View.INVISIBLE) {
            movies.setVisibility(View.VISIBLE);
            noMovies.setVisibility(View.INVISIBLE);
        }
        else if (movie.size() == 0 && movies.getVisibility() == View.VISIBLE) {
            movies.setVisibility(View.INVISIBLE);
            noMovies.setVisibility(View.VISIBLE);
        }
        moviesAdapter.notifyDataSetChanged();
    }

}