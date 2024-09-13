package acg.edu.taliouris.model;

import com.google.gson.Gson;
import java.util.Date;


public class Movie {
    private String title;
    private String type;
    private String director;
    private Date dor;


    public Movie(String title,String director, String type, Date dor) {
        this.title = title;
        this.director = director;
        this.type = type;
        this.dor = dor;
    }

    public Movie(String json){
        Gson gson = new Gson();
        Movie movie = gson.fromJson(json,Movie.class);
        title = movie.title;
        director = movie.director;
        type = movie.type;
        dor = movie.dor;
    }

    public String getTitle() { return title; }

    public String getDirector() { return director; }

    public String getType() { return type; }

    public Date getDor() { return dor; }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }



}
