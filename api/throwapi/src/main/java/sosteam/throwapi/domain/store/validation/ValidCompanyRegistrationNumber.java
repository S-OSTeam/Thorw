package sosteam.throwapi.domain.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sosteam.throwapi.domain.store.validation.validator.companyRegistrationNumber.CompanyRegistrationNumberValidator;

import java.lang.annotation.*;

/**
 * 사업자 등록 번호 형식은
 * XXX - XX - XXXXX 입니다.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CompanyRegistrationNumberValidator.class})
public @interface ValidCompanyRegistrationNumber {

    String message() default "올바른 사업자 등록번호 형식이 아닙니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
