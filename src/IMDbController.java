import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import Bean.Director;
import Bean.Movie;

import DAO.MovieDataDAO;

import com.squareup.okhttp.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IMDbController {
    public IMDbController(){
    }

    public String retrieve_json(String URL) {
        String response_json = null;
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create(mediaType, "{}");
            Request request = new Request.Builder()
                    .url(URL)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            response_json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response_json;

    }

    public List<Director> json2directors(String input) {
        List<Director> directors = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = json = (JSONObject) parser.parse(input);
            JSONArray crews_json = (JSONArray) json.get("crew");
            Iterator<JSONObject> iterator = crews_json.iterator();
            while (iterator.hasNext()) {
                JSONObject movie_json = iterator.next();

                if (movie_json.get("job").toString().equals("Director")) {
                    try{
                        Director director = new Director(
                                Integer.valueOf(movie_json.get("id").toString()),
                                movie_json.get("name").toString(),
                                movie_json.get("profile_path").toString()
                        );
                        directors.add(director);
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return directors;
    }


    public List<Movie> json2movies(List<Movie> movies, String input) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(input);
            JSONArray movies_json = (JSONArray) json.get("results");
            Iterator<JSONObject> iterator = movies_json.iterator();
            while (iterator.hasNext()) {
                JSONObject movie_json = iterator.next();
                int movie_id = Integer.valueOf(movie_json.get("id").toString());
                Movie movie = new Movie(
                        movie_id,
                        movie_json.get("title").toString(),
                        movie_json.get("overview").toString(),
                        movie_json.get("original_title").toString());
                String credit_URL = "https://api.themoviedb.org/3/movie/" + movie_id + "/credits?api_key=bbb0e77b94b09193e6f32d5fac7a3b9c";
                String credits = retrieve_json(credit_URL);
                List<Director> directors = json2directors(credits);
                movie.setDirectors(directors);
                movies.add(movie);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> retrieveMovies() throws  IOException {
        List<Movie> movies = new ArrayList<>();
        String URL = "https://api.themoviedb.org/3/movie/now_playing?region=GR&api_key=bbb0e77b94b09193e6f32d5fac7a3b9c";
        String str = retrieve_json(URL);
        // Convert json to movies with directors
        json2movies(movies, str);
        return movies;
    }

    public void storeMovies(List<Movie> movies) throws SQLException, ClassNotFoundException {
        MovieDataDAO movieDataDAO = new MovieDataDAO();
        movieDataDAO.updateMovieData(movies);
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        IMDbController controller = new IMDbController();
        List<Movie> movies = controller.retrieveMovies();

        controller.storeMovies(movies);
    }
}
