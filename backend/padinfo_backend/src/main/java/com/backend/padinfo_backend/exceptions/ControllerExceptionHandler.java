package com.backend.padinfo_backend.exceptions;

import com.backend.padinfo_backend.exceptions.game.GameDeleteException;
import com.backend.padinfo_backend.exceptions.game.GameNotFoundException;
import com.backend.padinfo_backend.exceptions.player.PlayerDeleteException;
import com.backend.padinfo_backend.exceptions.player.PlayerNotFoundException;
import com.backend.padinfo_backend.exceptions.tournament.TournamentDeleteException;
import com.backend.padinfo_backend.exceptions.tournament.TournamentNotFoundException;
import com.backend.padinfo_backend.exceptions.userInfo.UserInfoDeleteException;
import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler
{
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handlevalidationArgumentErrors(MethodArgumentNotValidException ex)
    {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->
                {
                   String fieldName = ((FieldError) error).getField();
                   String erroMessage = error.getDefaultMessage();
                   errores.put(fieldName, erroMessage);
                }
        );

        logger.error("Error en la validación de los datos: " + ex.getMessage(), ex);

        return new ResponseEntity<Response>(Response.validationError(errores), HttpStatus.BAD_REQUEST);
    }

    /*
        NOT FOUND EXCEPTIONS
     */
    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Response> handleGameNotFoundError(GameNotFoundException ex)
    {
        logger.error("Partido no encontrado: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.notFoundError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserInfoNotFoundException.class)
    public ResponseEntity<Response> handleUserInfoNotFoundError(UserInfoNotFoundException ex)
    {
        logger.error("Usuario no encontrado: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.notFoundError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TournamentNotFoundException.class)
    public ResponseEntity<Response> handleTournamentNotFoundError(TournamentNotFoundException ex)
    {
        logger.error("Torneo no encontrado: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.notFoundError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Response> handlePlayerNotFoundError(PlayerNotFoundException ex)
    {
        logger.error("Jugador no encontrado: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.notFoundError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /*
        DELETE EXCEPTIONS
     */
    @ExceptionHandler(GameDeleteException.class)
    public ResponseEntity<Response> handlerDeleteGameError(GameDeleteException ex)
    {
        logger.error("Error al borrar el partido: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value()
                                                        , ex.getMessage())
                                                        , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserInfoDeleteException.class)
    public ResponseEntity<Response> handlerDeleteUserInfoError(UserInfoDeleteException ex)
    {
        logger.error("Error al borrar el usuario: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value()
                , ex.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TournamentDeleteException.class)
    public ResponseEntity<Response> handlerDeleteTournamentError(TournamentDeleteException ex)
    {
        logger.error("Error al borrar el torneo: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value()
                , ex.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PlayerDeleteException.class)
    public ResponseEntity<Response> handlerDeletePlayerError(PlayerDeleteException ex)
    {
        logger.error("Error al borrar el jugador: " + ex.getMessage(), ex);

        return new ResponseEntity<>(Response.generalError(HttpStatus.INTERNAL_SERVER_ERROR.value()
                , ex.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
