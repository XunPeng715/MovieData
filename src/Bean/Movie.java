package Bean;

import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String description;
    private String originalTitle;
    private List<Director> directors;

    public Movie(int movieId, String title, String description, String originalTitle) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.originalTitle = originalTitle;
    }

    public int getMovieId() {
        return movieId;
    }


    public String getTitle() {

        return title;
    }


    public String getDescription() {

        return description;
    }

    public String getOriginalTitle() {

        return originalTitle;
    }

    public List<Director> getDirectors() {

        return directors;
    }

    public void setDirectors(List<Director> directors) {

        this.directors = directors;
    }
}
