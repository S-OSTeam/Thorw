package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.LeaderBoardDto;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageService {
    private final UserRepository userRepository;

    /**
     * 10위까지 리더보드 정보 넣기. {userName,mileage,ranking}로 리턴
     */
    public Set<LeaderBoardDto> createLeaderBoard(){
        Set<User> top10Users= userRepository.searchTop10UsersByMileage();

        AtomicLong rank = new AtomicLong(1);

        Set<LeaderBoardDto> leaderBoardDtos = top10Users.stream()
                .map(user -> new LeaderBoardDto(user.getUserInfo().getUserName(),
                        user.getMileage().getAmount(),
                        rank.getAndIncrement()))
                .collect(Collectors.toSet());

        return leaderBoardDtos;
    }

    /**
     * 해당 user의 {userName,mileage,ranking} 정보 얻기
     * @param inputId
     */
    public LeaderBoardDto searchMyRankingByInputId(String inputId){
        User user = userRepository.searchByInputId(inputId);
        Long mileage = user.getMileage().getAmount();

        // 해당 사용자의 순위를 계산
        Long userRank = userRepository.findRankByMileage(mileage);

        return new LeaderBoardDto(user.getUserInfo().getUserName(), mileage,userRank);
    }
}
