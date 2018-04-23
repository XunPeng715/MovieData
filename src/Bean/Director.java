package Bean;

public class Director {
    private int directorId;
    private String name;
    private String link;

    public Director(int directorId, String name, String link) {
        this.directorId = directorId;
        this.name = name;
        this.link = link;
    }

    public int getDirectorId() {
        return directorId;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

}