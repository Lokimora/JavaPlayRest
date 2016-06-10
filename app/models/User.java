package models;


import com.avaje.ebean.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by lokimora on 6/8/2016.
 */

@Entity
@Table(name = "users", schema = "public")
public class User extends Model {

    public User(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;
    }
    public User(){}

    @Id
    public Long id;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private String login;

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return login;
    }

    private String password;

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    @ManyToMany
    @JoinTable(name = "user_roles", schema = "public",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    public void setRoles(List<Role> roles){
        this.roles = roles;
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void AddRole(Role role){
        if(roles == null){
            roles = new ArrayList();
        }
        if(!roles.contains(role)){
            roles.add(role);
        }


    }

    public void RemoveRole(Role role){
        if(roles != null){
            roles.removeIf(p -> p.id == role.id);
        }
    }

    public UserResult Validate(){
        List<String> errors = new ArrayList<String>();

        if(name == null || name.isEmpty()){
            errors.add("Поле имя обязательно");
        }

        if(login == null || login.isEmpty()){
            errors.add("Поле логин обязательно");
        }

        if(password == null || password.isEmpty()){

                errors.add("Поле пароль обязательно");
        }
        else {
            Pattern p = Pattern.compile("[A-Z]");
            Matcher m = p.matcher(password);

            if(!m.find()){
                errors.add("Пароль должен содержать как минимум 1 букву заглавного регистра");
            }
        }

        if(roles != null && roles.size() > 0){


            List<Long> ids = roles.stream().map(p -> p.id).collect(Collectors.toList());

            com.avaje.ebean.Query<Role> query = Ebean.createQuery(Role.class);
            query.where(Expr.in("id", ids));

            List<Role> existRoles =  query.findList();

            if(existRoles.size() != roles.size()){
                errors.add("Одна или несколько из переданных ролей не сущесвует");
            }
        }

        if(errors.size() > 0){
            return new UserResult(false, errors);
        }

        return new UserResult(true, errors);




    }






}
