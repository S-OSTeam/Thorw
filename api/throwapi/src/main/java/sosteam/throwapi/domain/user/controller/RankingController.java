package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.user.controller.request.RankingRequest;
import sosteam.throwapi.domain.user.controller.response.RankingResponse;
import sosteam.throwapi.domain.user.entity.dto.user.RankingDto;
import sosteam.throwapi.domain.user.service.MileageSearchService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final MileageSearchService mileageSearchService;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<RankingResponse>> createLeaderBoard(){
        log.debug("CREATELEADERBOARDCONTROLLER");
        List<RankingDto> leaderBoard = mileageSearchService.createLeaderBoard();

        List<RankingResponse> rankingResponses = leaderBoard.stream()
                .map(rankingDto -> new RankingResponse(
                        rankingDto.getUserName(),
                        rankingDto.getMileage(),
                        rankingDto.getRanking()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(rankingResponses);
    }

    @PostMapping("/userranking")
    public ResponseEntity<RankingResponse> searchRankingByuserId(@RequestBody @Valid RankingRequest request){
        RankingDto rankingDto = mileageSearchService.searchUserRankingByInputId(request.getInputId());

        RankingResponse rankingResponse = new RankingResponse(
                rankingDto.getUserName(),
                rankingDto.getMileage(),
                rankingDto.getRanking());

        return ResponseEntity.ok(rankingResponse);
    }
}
