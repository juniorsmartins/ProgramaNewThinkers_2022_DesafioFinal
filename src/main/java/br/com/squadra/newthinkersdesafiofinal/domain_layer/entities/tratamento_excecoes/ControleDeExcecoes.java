package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public final class ControleDeExcecoes {

    @Autowired
    private MessageSource messageInternacionalizada;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorsPersonalizadas excecaoRecursoNaoEncontradoException(RecursoNaoEncontradoException recursoNaoEncontradoException) {
        return new ApiErrorsPersonalizadas(HttpStatus.NOT_FOUND.toString(), recursoNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(RegrasDeNegocioVioladasException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorsPersonalizadas excecaoRegrasDeNegocioVioladasException(RegrasDeNegocioVioladasException regrasDeNegocioVioladasException) {
        return new ApiErrorsPersonalizadas(HttpStatus.CONFLICT.toString(),
                regrasDeNegocioVioladasException.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiErrorsPersonalizadas excecaoNaConversaoDeValoresEntreTipos(NumberFormatException numberFormatException) {
        return new ApiErrorsPersonalizadas(HttpStatus.NOT_ACCEPTABLE.toString(),
                MensagemPadrao.CODIGO_COM_FORMATO_INVALIDO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> excecaoMethodNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<ApiErrorsValidation> errosDeValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageInternacionalizada.getMessage(erro, LocaleContextHolder.getLocale());
            ApiErrorsValidation erroPersonalizadoParaRetorno = new ApiErrorsValidation(
                    HttpStatus.BAD_REQUEST.toString(), mensagem, erro.getField(), erro.getCode());
            errosDeValidacao.add(erroPersonalizadoParaRetorno);
        });
        return ResponseEntity.badRequest().body(errosDeValidacao.get(0));
    }
}
