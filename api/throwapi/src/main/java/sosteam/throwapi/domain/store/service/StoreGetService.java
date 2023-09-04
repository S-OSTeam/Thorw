package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.exception.NoStoreOfUserException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.Direction;
import sosteam.throwapi.domain.store.util.GeometryUtil;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserInfoService;
import sosteam.throwapi.global.exception.exception.NoContentException;
import sosteam.throwapi.global.exception.exception.NotFoundException;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreGetService {
    private final StoreRepository storeRepository;
    private final UserInfoService userInfoService;
    private final JwtTokenService jwtTokenService;

    /**
     * 1: Make two points that are (distance) km apart from the current location
     * - One to the northeast and one to the southwest
     * 2: Make a ST_LINESTRING with these points
     * 3: Use MBR(Minimum boundary Rectangular)_Contains function
     * 3 steps will be done using QueryDsl(using StringTemplate)
     * -----------------------------------------------------------
     * MBRContains(A,B):
     * if MBR of A contains B, return 1 else 0
     * @param storeInRadiusDto Current User's Location and demanded distance
     *
     * @return
     * if null, return 404 (NOT FOUND)
     * if there are no stores, return 204 (No Content)
     * if there are stores, return 200 (StoreList)
     */
    public Set<StoreDto> searchStoreInRadius(StoreInRadiusDto storeInRadiusDto) {
        log.debug("Start Searching Stores around = {}", storeInRadiusDto);

        // 1:
        Point northEast = GeometryUtil.calculate(
                storeInRadiusDto.getLatitude(),
                storeInRadiusDto.getLongitude(),
                storeInRadiusDto.getDistance(),
                Direction.NORTHEAST.getBearing()
        );
        Point southWest = GeometryUtil.calculate(
                storeInRadiusDto.getLatitude(),
                storeInRadiusDto.getLongitude(),
                storeInRadiusDto.getDistance(),
                Direction.SOUTHWEST.getBearing()
        );
        log.debug(String.format("NorthEast(%s %s) SRID:%s", northEast.getX(), northEast.getY(), northEast.getSRID()));
        log.debug(String.format("SouthWest(%s %s) SRID:%s", southWest.getX(), southWest.getY(), southWest.getSRID()));
        GeometryFactory geometryFactory = GeometryUtil.getGeometryFactory();
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(northEast.getX(), northEast.getY()),
                new Coordinate(southWest.getX(), southWest.getY())
        };

        LineString lineString = geometryFactory.createLineString(coordinates);

        storeInRadiusDto.setLineString(lineString);

        log.debug("Created LineString = {}", lineString);

        Optional<Set<StoreDto>> stores = storeRepository.searchStoreInRadius(storeInRadiusDto);

        if(stores.isEmpty()) throw new NotFoundException();
        if(stores.get().isEmpty()) throw new NoContentException();
        return stores.get();
    }

    /**
     * 검색 이름이 포함된 가게들을 반환
     * @param name 검색 이름
     * @return 가게 정보들
     */
    public Set<StoreDto> searchStoreByName(String name) {
        log.debug("Start searching Stores which name is {}", name);
        Optional<Set<StoreDto>> storeDtos = storeRepository.searchByName(name);
        if(storeDtos.isEmpty()) throw new NotFoundException();
        if(storeDtos.get().isEmpty()) throw new NoContentException();

        return storeDtos.get();
    }

    public StoreDto searchByCRN(String crn) {
        Optional<StoreDto> storeDto = isExistByCRN(crn);
        if(storeDto.isEmpty()) throw new NoSuchStoreException();
        return storeDto.get();
    }


    public Optional<StoreDto> isExistByCRN(String crn) {
        return storeRepository.searchByCRN(crn);
    }

    public Set<StoreDto> searchMyStores(String accessToken) {
        // 요청 사용자 정보 가져오기
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(accessToken)
        );
        User user = userInfoService.searchByInputId(userInfoDto);
        Optional<Set<StoreDto>> storeDtos = storeRepository.searchMyStores(user.getId());
        if(storeDtos.isEmpty()) throw new NoStoreOfUserException();
        if(storeDtos.get().isEmpty()) throw new NoContentException();
        return storeDtos.get();
    }
}
