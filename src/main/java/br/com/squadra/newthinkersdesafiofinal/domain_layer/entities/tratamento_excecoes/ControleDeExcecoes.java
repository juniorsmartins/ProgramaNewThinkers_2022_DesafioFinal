package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControleDeExcecoes {

    @Autowired
    private MessageSource messageInternacionalizada;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlerRecursoNaoEncontradoException(RecursoNaoEncontradoException recursoNaoEncontradoException) {
        return new ApiErrors(HttpStatus.NOT_FOUND.toString(), recursoNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(RegrasDeNegocioVioladasException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handlerRegrasDeNegocioVioladasException(RegrasDeNegocioVioladasException regrasDeNegocioVioladasException) {
        return new ApiErrors(HttpStatus.CONFLICT.toString(), regrasDeNegocioVioladasException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<ApiErrors> errosDeValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageInternacionalizada.getMessage(erro, LocaleContextHolder.getLocale());
            ApiErrors erroPersonalizadoParaRetorno = new ApiErrors(
                    HttpStatus.BAD_REQUEST.toString(), mensagem);
            errosDeValidacao.add(erroPersonalizadoParaRetorno);
        });
        return ResponseEntity.badRequest().body(errosDeValidacao.get(0));
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ApiErrors> handlerMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<ApiErrors> errosDeValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageInternacionalizada.getMessage(erro, LocaleContextHolder.getLocale());
            ApiErrors erroPersonalizadoParaRetorno = new ApiErrors(
                    HttpStatus.BAD_REQUEST.toString(), erro.getCode(), erro.getField(), mensagem);
            errosDeValidacao.add(erroPersonalizadoParaRetorno);
        });
        return errosDeValidacao;
    }*/

}
