package sosteam.throwapi.domain.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull
@Target({ElementType.FIELD})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Range(min = -180,max = 180, message = "경도 범위 : -180 ~ 180")
public @interface ValidLongitude {
    String message() default "경도 범위 : -180 ~ 180";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
