package kg.ksucta.units;

import kg.ksucta.main.Room;

public interface Unit {

    String getName();

    Integer getHealth();

    void setHealth(Integer health);

    Room getCurrentRoom();
}
