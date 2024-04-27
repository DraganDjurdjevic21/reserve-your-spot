package com.prodyna.reserveyourspot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * The service exception handler.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ServiceExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    /**
     * Handel RYSValidationException.
     *
     * @param req HttpServletRequest object.
     * @param exp RYSValidationException object.
     * @return {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = RYSValidationException.class)
    public ResponseEntity<Object> handeRYSValidationException(HttpServletRequest req, RYSValidationException exp) {
        final ErrorResponseDTO errorResponse = createErrorResponse(exp, exp.getErrorCode(), req.getServletPath());
        LOG.error(exp.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, exp.getErrorCode().getStatus());
    }

    /**
     * Handel MethodArgumentNotValidException.
     *
     * @param req HttpServletRequest object.
     * @param exp MethodArgumentNotValidException object.
     * @return {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handeMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException exp) {
        final ErrorCode errorCode = ErrorCode.ARGUMENT_NOT_VALID;
        final ErrorResponseDTO errorResponse = new ErrorResponseDTO(String.format(errorCode.getMessage(), Objects.requireNonNull(exp.getFieldError()).getField()),
                Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase(),
                exp.getClass().getName(), req.getServletPath());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /*
        /**
         * Handel TransactionSystemException.
         *
         * @param req HttpServletRequest object.
         * @param exp TransactionSystemException object.
         * @return {@link ResponseEntity} object.
         */
    @ExceptionHandler(value = TransactionSystemException.class)
    public ResponseEntity<Object> handelTransactionSystemException(HttpServletRequest req, TransactionSystemException exp) {
        final ErrorCode errorCode = ErrorCode.DB_CONNECTION_ERROR;
        final ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorCode.getMessage(), Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase(),
                exp.getClass().getName(), req.getServletPath());
        LOG.error(exp.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }


    /**
     * Handel TransientDataAccessResourceException.
     *
     * @param req HttpServletRequest object.
     * @param exp TransientDataAccessResourceException object.
     * @return {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = TransientDataAccessResourceException.class)
    public ResponseEntity<Object> handelTransientDataAccessResourceException(HttpServletRequest req, TransientDataAccessResourceException exp) {
        final ErrorCode errorCode = ErrorCode.DB_CONNECTION_ERROR;
        final ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorCode.getMessage(), Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase(),
                exp.getClass().getName(), req.getServletPath());
        LOG.error(exp.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * Handel AccessDeniedException.
     *
     * @param req HttpServletRequest object.
     * @param exp AccessDeniedException object.
     * @return {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest req, AccessDeniedException exp) {
        final ErrorCode errorCode = ErrorCode.FORBIDDEN;
        final ErrorResponseDTO errorResponse = new ErrorResponseDTO(exp.getMessage(), Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase() + " - " + errorCode.getMessage(),
                exp.getClass().getName(), req.getServletPath());
        LOG.error(exp.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * Handel general Exception.
     *
     * @param req HttpServletRequest object.
     * @param exp the exception
     * @return {@link ResponseEntity} object.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalExceptionHandler(HttpServletRequest req, Exception exp) {
        final HttpStatus badRequest = ErrorCode.INTERNAL_SERVER_ERROR.getStatus();
        final ErrorResponseDTO errorResponse = createErrorResponse(exp, ErrorCode.INTERNAL_SERVER_ERROR, req.getServletPath());
        LOG.error(exp.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    /**
     * Create ErrorResponseDTO object.
     *
     * @param exp       the exception
     * @param errorCode ErrorCode value
     * @param path      request path.
     * @return {@link ErrorResponseDTO} object.
     */
    private ErrorResponseDTO createErrorResponse(Exception exp, ErrorCode errorCode, String path) {
        return new ErrorResponseDTO(exp.getMessage(), Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase() + " - " + errorCode.getMessage(),
                exp.getClass().getName(), path);
    }
}
