package sosteam.throwapi.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.exception.BiznoAPIException;
import sosteam.throwapi.domain.store.exception.NoSuchRegistrationNumberException;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;

@Service
@RequiredArgsConstructor
public class BiznoService {
    private final BiznoAPI biznoAPI;

    /**
     * BIZNO API를 이용하여 해당 사업자 번호가 국세청에 등록된 번호인지 확인
     * @param number 사업자 등록 번호
     * @return result 사업자 등록 번호로 등록된 가게 이름 -> storeName
     * response.getResultCode() :
     *  -1 : 미등록 사용자 -> Wrong API-KEY
     *  -2 : 파라메터 오류`
     *  -3 : 1일 100건 조회수 초과
     *  9 : 기타 오류
     *  -10 : 해당 번호 존재 X
     */
    public String confirmCompanyRegistrationNumber(String number,String accessToken){
        BiznoApiResponse response = biznoAPI.confirmCompanyRegistrationNumber(number,accessToken);
        if( response == null || response.getTotalCount() == 0) throw new NoSuchRegistrationNumberException();
        if(response.getResultCode() < 0) throw new BiznoAPIException(response.getResultCode());
        return response.getItems().get(0).getCompany();
    }
}
