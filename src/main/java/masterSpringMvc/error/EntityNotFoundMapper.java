package masterSpringMvc.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityNotFoundMapper {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resource do not exist")
	public void handleNotFound() {}
}
