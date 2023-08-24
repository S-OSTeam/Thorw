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
@Range(min = 0,max = 5, message = "0km <= 탐색 거리 <= 5km")
public @interface ValidDemandedDistance {
    String message() default "0km <= 탐색 거리 <= 5km";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
