package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokimora on 6/9/2016.
 */
public class UserResult {

    public UserResult(boolean success, List<String> errors){
        this.success =  success;
        this.errors = errors;
    }

    public UserResult(){}

    private boolean success;

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean getSuccess(){
        return success;
    }

    private List<String> errors;

    public void setErrors(List<String> errors){
        this.errors = errors;
    }

    public List<String> getErrors(){
        return errors;
    }

    public void addError(String message){
        if(errors == null){
            errors = new ArrayList<String>();
        }
        errors.add(message);
    }

}
