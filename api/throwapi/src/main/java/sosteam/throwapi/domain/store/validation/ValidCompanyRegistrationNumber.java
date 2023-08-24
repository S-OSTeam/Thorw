package sosteam.throwapi.domain.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

/**
 * 사업자 등록 번호 형식은
 * XXX - XX - XXXXX 입니다.
 */
@NotNull
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{5}|[0-9]{10}$", message = "올바른 사업자 등록번호 형식이 아닙니다.")
public @interface ValidCompanyRegistrationNumber {

    String message() default "올바른 사업자 등록번호 형식이 아닙니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
