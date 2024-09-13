package acg.edu.taliouris.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import acg.edu.taliouris.R;
import acg.edu.taliouris.model.Movie;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movies = movie;
    }

    @Override
    public int getCount(){ return movies.size();}

    @Override
    public Object getItem(int position){ return movies.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.adapter, parent, false);
        }


        Movie movie = movies.get(position);
        ((TextView) convertView.findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) convertView.findViewById(R.id.director)).setText(movie.getDirector());
        ImageView type = convertView.findViewById(R.id.type);
        switch (movie.getType()) {
            case "Romance":
                type.setImageResource(R.mipmap.romance);
                break;
            case "Action":
                type.setImageResource(R.mipmap.action);
                break;
            case "Horror":
                type.setImageResource(R.mipmap.horror);
                break;
            case "Sci-fi":
                type.setImageResource(R.mipmap.scifi);
                break;
            default:
                type.setImageResource(R.mipmap.unknown);
        }


        return convertView;

    }

}