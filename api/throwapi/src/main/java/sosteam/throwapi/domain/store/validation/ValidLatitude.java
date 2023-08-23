package sosteam.throwapi.domain.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull
@Target({ElementType.FIELD})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Range(min = -90,max = 90, message = "위도 범위 : -90 ~ 90")
public @interface ValidLatitude {

    String message() default "위도 범위 : -90 ~ 90";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
