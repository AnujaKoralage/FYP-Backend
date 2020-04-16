package fyp.common.entity;

import javax.persistence.Entity;

@Entity
public class Permission extends BaceEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
