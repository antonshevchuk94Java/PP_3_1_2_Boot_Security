package ru.kata.spring.boot_security.demo.exception;

public class UserDeleteException extends RuntimeException{
    public UserDeleteException (String message){
        super(message);
    }

}
