package DAO;


import Bean.Movie;
import Bean.Director;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieDataDAO implements DAO {
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieData", "root", "Peng1992");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
        }
        return connection;
    }

    public void updateMovieData(List<Movie> movies) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        for (Movie movie : movies) {
            int movie_id = movie.getMovieId();
            String title = movie.getTitle();
            String description = movie.getDescription();
            String original_title = movie.getOriginalTitle();
            try {
                int movie_table = insertMovie(movie_id , title, description, original_title, connection);
            } catch (MySQLIntegrityConstraintViolationException | MySQLSyntaxErrorException e) {
                continue;
            }
            for (Director director : movie.getDirectors()) {

                int director_id = director.getDirectorId();
                String name = director.getName();
                String profile_path = director.getLink();
                try {
                    int director_table = insertDirector(director_id , name, profile_path, connection);
                    int movie_director_table = insertMovie_director(movie_id, director_id, connection);
                } catch (MySQLIntegrityConstraintViolationException | MySQLSyntaxErrorException e) {
                    continue;
                }

            }
        }
    }

    public int insertMovie(int movie_id, String title, String description, String original_title, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO MOVIE (movie_id , title, description, original_title) VALUES ("
                + String.valueOf(movie_id) + ",'" + title + "','" + description + "','" + original_title + "')";
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(query);

        return result;
    }

    public int insertDirector(int director_id, String name, String profile_path, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO DIRECTOR (director_id , name, profile_path) VALUES ("
                + String.valueOf(director_id) + ",'" + name + "','" + profile_path + "')";
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(query);

        return result;
    }

    public int insertMovie_director(int movie_id, int director_id, Connection connection) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO MOVIE_DIRECTOR (movie_id, director_id) VALUES ("
                + String.valueOf(movie_id) + "," + String.valueOf(director_id) + ")";
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(query);

        return result;
    }
}
