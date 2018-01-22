package kg.ksucta.items;

public class Artifact implements Item {

    private String name;
    private String description;
    private int points;

    public Artifact(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getDamageValue() {
        return null;
    }

    @Override
    public Integer getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}
