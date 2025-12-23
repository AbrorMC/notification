package uz.uzumtech.notification.exception;

import org.springframework.http.HttpStatus;
import uz.uzumtech.notification.constant.enums.ErrorType;

public class DuplicateUserException extends ApplicationException{
    public DuplicateUserException(String message) {
        super(10011, message, HttpStatus.CONFLICT, ErrorType.INTERNAL);
    }
}
