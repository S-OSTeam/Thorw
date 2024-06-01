package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.user.controller.request.RankingRequest;
import sosteam.throwapi.domain.user.controller.request.user.IdDuplicateRequest;
import sosteam.throwapi.domain.user.controller.response.RankingResponse;
import sosteam.throwapi.domain.user.entity.dto.user.RankingDto;
import sosteam.throwapi.domain.user.service.MileageSearchService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankingController {
    private final MileageSearchService mileageSearchService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/getStoreList")
    public ResponseEntity<Optional<Store>> getStoreListByInputId(@RequestBody @Valid String inputId){
        log.debug("getStoreListByInputId()");
        Optional<Store> storeList = mileageSearchService.getStoreListByInputId(inputId);
        return ResponseEntity.ok(storeList);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<RankingResponse>> createLeaderBoard() {
        log.debug("CREATELEADERBOARDCONTROLLER");
        List<RankingDto> leaderBoard = mileageSearchService.createLeaderBoard();

        List<RankingResponse> rankingResponses = leaderBoard.stream()
                .map(rankingDto -> new RankingResponse(
                        rankingDto.getInputId(),
                        rankingDto.getUserName(),
                        rankingDto.getMileage(),
                        rankingDto.getRanking()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(rankingResponses);
    }

    @GetMapping
    public ResponseEntity<RankingResponse> searchRankingByuserId(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token) {
        String accessToken = token.substring(7);
        String inputId = jwtTokenService.extractSubject(accessToken);
        RankingDto rankingDto = mileageSearchService.searchUserRankingByInputId(inputId);


        RankingResponse rankingResponse = new RankingResponse(
                rankingDto.getInputId(),
                rankingDto.getUserName(),
                rankingDto.getMileage(),
                rankingDto.getRanking());

        return ResponseEntity.ok(rankingResponse);
    }
}
