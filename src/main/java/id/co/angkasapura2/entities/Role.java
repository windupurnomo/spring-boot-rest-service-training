package id.co.angkasapura2.entities;


import id.co.angkasapura2.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    private int id;
    private String name;

    public Role() {
    }

    public Role(RoleEnum roleEnum){
        this.id = roleEnum.getValue();
        this.name = roleEnum.name();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
