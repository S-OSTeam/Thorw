package sosteam.throwapi.domain.store.validation.validator.companyRegistrationNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import sosteam.throwapi.domain.store.validation.ValidCompanyRegistrationNumber;

@Slf4j
public class CompanyRegistrationNumberValidator implements ConstraintValidator<ValidCompanyRegistrationNumber, String> {

    @Value("${bizno.api.key}")
    private String apiKey;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // if input is null, return false
        if(value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("널이어서는 안됩니다").addConstraintViolation();
            return false;
        }
        // if input doesn't match Pattern, return false
        // XXX-XX-XXXXX
        // or
        // XXXXXXXXXX
        if(!value.matches("^[0-9]{3}-[0-9]{2}-[0-9]{5}|[0-9]{10}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("올바른 사업자 등록번호 형식이 아닙니다.").addConstraintViolation();
            return false;
        }

        // Call Bizno-api to Confirm CompanyRegistrationNumber
        WebClient webClient = WebClient.builder()
                .baseUrl("https://bizno.net/api/fapi?")
                .build();
        BiznoApiResponse response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .queryParam("key",apiKey)
                                        .queryParam("gb",1)
                                        .queryParam("q",value)
                                        .queryParam("type","json")
                                        .build()
                        )
                        .retrieve()
                        .bodyToMono(BiznoApiResponse.class)
                        .block();

        log.debug("BIZNO-API-RESPONSE={}",response);

        // if response is null or response.totalCount is zero, return false
        if(response == null || response.getTotalCount() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("등록되지 않은 사업자 번호입니다.").addConstraintViolation();
            return false;
        }

        return true;
    }

}
