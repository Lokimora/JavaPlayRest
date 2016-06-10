package controllers;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.google.inject.Inject;
import models.User;
import models.UserResult;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokimora on 6/8/2016.
 */
public class UserController extends Controller {

    private final Form<User> userForm;

    @Inject
    public UserController(FormFactory formFactory) {
        this.userForm = formFactory.form(User.class);
    }

    public  Result list() {

        List<User> users = Ebean.find(User.class).findList();

        return ok(Json.toJson(users));
    }

    public Result get(Long id) {

        User user = User.db().find(User.class).where().eq("id", id).findUnique();

        if(user == null)
            return notFound();

        return ok(Json.toJson(user));
    }

    public Result delete() {

        Form<User> form = userForm.bindFromRequest();
        User user = form.get();

        User.db().delete(User.class, user.id);
        return ok();
    }

    public Result add(){

        Form<User> form = userForm.bindFromRequest();
        User user = form.get();
        UserResult validation = user.Validate();

        if(validation.getSuccess()) {
            User.db().insert(user);
        }

        return ok(Json.toJson(validation));
    }

    public Result edit(){
        Form<User> form = userForm.bindFromRequest();

        User user = form.get();

        if(user.id == null || user.id == 0) {
            return badRequest("необходимо передать идентификатор");
        }

        UserResult validation = user.Validate();

        User existingUser = User.db().find(User.class, user.id);

        if(validation.getSuccess()){

            if(existingUser != null){
                User.db().update(user);
            }
            else {
                validation.setSuccess(false);
                validation.addError("Пользователь не существует");
            }

        }

        return ok(Json.toJson(validation));

    }



}
