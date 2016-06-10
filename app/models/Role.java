package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by lokimora on 6/8/2016.
 */
@Entity
@Table(name = "roles", schema = "public")
public class Role extends Model {

    public Role(String name){
        this.name = name;
    }

    public Role() { }

    @Id
    public Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void SetUsers(List<User> users){
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {



        if(obj != null && obj instanceof Role){

            if(obj == this)
                return true;

            Role role = (Role) obj;
            return role.id == this.id;
        }

        return false;
    }
}
