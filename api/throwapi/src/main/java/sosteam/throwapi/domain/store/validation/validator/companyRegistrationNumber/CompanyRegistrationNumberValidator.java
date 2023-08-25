package sosteam.throwapi.domain.store.validation.validator.companyRegistrationNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;
import sosteam.throwapi.domain.store.validation.ValidCompanyRegistrationNumber;

@Slf4j
@RequiredArgsConstructor
public class CompanyRegistrationNumberValidator implements ConstraintValidator<ValidCompanyRegistrationNumber, String> {

    private final BiznoAPI biznoAPI;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // if input is null, return false
        if(value == null) {
            setContextMessage(context, "널이어서는 안됩니다");
            return false;
        }
        // if input doesn't match Pattern, return false
        // XXX-XX-XXXXX
        // or
        // XXXXXXXXXX
        if(!value.matches("^[0-9]{3}-[0-9]{2}-[0-9]{5}|[0-9]{10}$")) {
            setContextMessage(context, "올바른 사업자 등록번호 형식이 아닙니다.");
            return false;
        }

        // Call Bizno-api to Confirm CompanyRegistrationNumber
        BiznoApiResponse response = biznoAPI.confirmCompanyRegistrationNumber(value);

        // if response is null or response.totalCount is zero, return false
        if(response == null || response.getResultCode() == -1 || response.getTotalCount() == 0) {
            setContextMessage(context, "국세청에 등록되지 않은 사업자등록번호입니다.");
            return false;
        }
        int resultCode = response.getResultCode();
        if (resultCode == -2 || resultCode == -3 || resultCode == -9) {
            setContextMessage(context, "BIZNO-API:사업자 등록 번호 조회 API 관련 오류!!");
            return false;
        }

        return true;
    }

    private static void setContextMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }


}
