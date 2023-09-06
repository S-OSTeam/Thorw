package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.exception.ModifyMileageException;
import sosteam.throwapi.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageModifyService {
    private final UserRepository userRepository;

    public Long modifyMileage(String inputId, Long addMileage){
        Long mileage = userRepository.modifyMileageByInputId(inputId, addMileage);
        if(mileage==0){
            throw new ModifyMileageException();
        }
        return mileage;
    }
}
