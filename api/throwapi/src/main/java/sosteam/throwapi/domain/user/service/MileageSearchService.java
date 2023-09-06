package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.RankingDto;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageSearchService {
    private final UserRepository userRepository;

    /**
     * 10위까지 리더보드 정보 넣기. {userName,mileage,ranking}로 리턴
     */
    public List<RankingDto> createLeaderBoard(){
        log.debug("CREATELEADERBOARD");
        Set<User> top10Users= userRepository.searchTop10UsersByMileage();
        log.debug("CREATELEADERBOARDAFTER");

        AtomicLong rank = new AtomicLong(1);
        log.debug("ATOMIC");
        List<RankingDto> rankingDtos = top10Users.stream()
                .filter(user -> user.getUserInfo() != null && user.getMileage() != null)
                .sorted(Comparator.comparing(user -> -user.getMileage().getAmount()))  // 마일리지에 따라 내림차순 정렬
                .map(user -> new RankingDto(
                        user.getUserInfo().getUserName(),
                        user.getMileage().getAmount(),
                        rank.getAndIncrement()))
                .collect(Collectors.toList());
        log.debug("RANKINGDTO");
        return rankingDtos;
    }

    /**
     * 해당 user의 {userName,mileage,ranking} 정보 얻기
     * @param inputId
     */
    public RankingDto searchUserRankingByInputId(String inputId){
        User user = userRepository.searchByInputId(inputId);
        Long mileage = user.getMileage().getAmount();

        // 해당 사용자의 순위를 계산
        Long userRank = userRepository.findRankByMileage(mileage);

        return new RankingDto(user.getUserInfo().getUserName(), mileage,userRank);
    }
}
