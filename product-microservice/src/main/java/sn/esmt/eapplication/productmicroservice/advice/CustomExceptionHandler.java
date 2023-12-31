package sn.esmt.eapplication.productmicroservice.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.esmt.eapplication.productmicroservice.exceptions.RequestNotAcceptableException;
import sn.esmt.eapplication.productmicroservice.exceptions.ResourceNotFoundException;

import java.net.BindException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ProblemDetail handleSecurityException(Exception exception) {


        log.error("Error Message - {}", exception.getMessage());
        ProblemDetail problemDetail = null;

        if (exception instanceof ResourceNotFoundException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
        }

        if (exception instanceof RequestNotAcceptableException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(406), exception.getMessage());
        }

        return problemDetail;
    }
}
