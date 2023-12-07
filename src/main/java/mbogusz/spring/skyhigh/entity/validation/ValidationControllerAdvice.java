package mbogusz.spring.skyhigh.entity.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class ValidationControllerAdvice {

    private void processFieldError(FieldError error, Map<String, String> errorMap) {
        String fieldName = error.getField();
        String errorMessage = error.getDefaultMessage();
        errorMap.put(fieldName, errorMessage);
    }

    private void processObjectError(ObjectError error, Map<String, String> errorMap) {
        String errorMessage = error.getDefaultMessage();
        errorMap.put("message", errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            if(error instanceof FieldError) {
                processFieldError((FieldError)error, errors);
            } else {
                processObjectError(error, errors);
            }

        });
        return errors;
    }
}
