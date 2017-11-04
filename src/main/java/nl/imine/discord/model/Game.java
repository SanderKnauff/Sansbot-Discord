package nl.imine.discord.model;

public class Game {

    private String name;
    private GameType type;
    private String url;

    public Game(String name, GameType type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public enum GameType {
        GAME, STREAMING
    }
}
