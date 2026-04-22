package app.web.seunolo2.banksshop.exceptions;

public class CategoryNameAlreadyExistsException extends RuntimeException{

    public CategoryNameAlreadyExistsException(String message){
        super(message);
    }
}
