package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.anotacoes_personalizadas;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Executada em tempo de execução
@Target(ElementType.FIELD) // Onde será usada a anotação
@Constraint(validatedBy = BloqueioDeListaVaziaValidator.class) // Diz que é uma anotação de valiadação
public @interface NotEmptyList {
    String message() default "Lista - Preenchimento obrigatório! Não pode estar vazia.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
