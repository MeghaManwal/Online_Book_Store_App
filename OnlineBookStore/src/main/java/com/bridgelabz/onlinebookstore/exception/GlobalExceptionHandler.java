package com.bridgelabz.onlinebookstore.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

       @ExceptionHandler(BookStoreException.class)
       public ResponseEntity<ResponseDTO> handleAddressBookException(BookStoreException bookStoreException){
               log.error("Exception Occurred : " +bookStoreException.exceptionTypes.errorMsg);
               return new ResponseEntity<ResponseDTO>(new ResponseDTO(bookStoreException.exceptionTypes.errorMsg,
                                                               null), HttpStatus.BAD_REQUEST);
       }
	
       @ExceptionHandler(UserDataException.class)
       public ResponseEntity<ResponseDTO> userExceptionHandler(UserDataException userException) {
	        log.error("Exception Occurred : " +userException.exceptionTypes);
	        return new ResponseEntity<ResponseDTO>(new ResponseDTO(null,null),HttpStatus.BAD_REQUEST);
	}
	 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errMsg = errorList.stream()
						.map(objErr -> objErr.getDefaultMessage())
						.collect(Collectors.toList());
		ResponseDTO responseDTO = new ResponseDTO("Exceptions while processing REST Request",errMsg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
}
