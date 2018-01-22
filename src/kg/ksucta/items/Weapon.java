package kg.ksucta.items;

public class Weapon implements Item {

    private String name;
    private String description;
    private Integer damageValue;

    public Weapon(String name, Integer damageValue) {
        this.name = name;
        this.damageValue = damageValue;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(Integer damageValue) {
        this.damageValue = damageValue;
    }

    @Override
    public Integer getPoints() {
        return null;
    }

    public String getDescription() {
        return description;
    }
}
