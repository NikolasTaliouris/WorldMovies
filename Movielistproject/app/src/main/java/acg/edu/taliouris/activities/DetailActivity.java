package acg.edu.taliouris.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import acg.edu.taliouris.R;
import acg.edu.taliouris.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = new Movie(getIntent().getStringExtra("movie"));
        ((TextView) findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.director)).setText(movie.getDirector());
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        String type = movie.getType();
        if (type.isEmpty()) {
            type = getString(R.string.unknown_type);
        }
        ((TextView) findViewById(R.id.info)).setText(String.format(getString(R.string.info), type, sdf.format(movie.getDor())));
        switch (type) {
            case "Romance":
                ((ImageView) findViewById(R.id.type)).setImageResource(R.mipmap.romance);
                break;
            case "Action":
                ((ImageView) findViewById(R.id.type)).setImageResource(R.mipmap.action);
                break;
            case "Horror":
                ((ImageView) findViewById(R.id.type)).setImageResource(R.mipmap.horror);
                break;
            case "Sci-fi":
                ((ImageView) findViewById(R.id.type)).setImageResource(R.mipmap.scifi);
                break;
            default:
                ((ImageView) findViewById(R.id.type)).setImageResource(R.mipmap.unknown);
        }




    }
}