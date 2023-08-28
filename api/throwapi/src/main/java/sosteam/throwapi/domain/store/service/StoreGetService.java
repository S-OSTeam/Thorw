package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.global.exception.exception.NoContentException;
import sosteam.throwapi.global.exception.exception.NotFoundException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.Direction;
import sosteam.throwapi.domain.store.util.GeometryUtil;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreGetService {
    private final StoreRepository storeRepository;

    /**
     * 1: Make two points that are (distance) km apart from the current location
     * - One to the northeast and one to the southwest
     * 2: Make a ST_LINESTRING with these points
     * 3: Use MBR(Minimum boundary Rectangular)_Contains function
     * 3 steps will be done using QueryDsl(using StringTemplate)
     * -----------------------------------------------------------
     * MBRContains(A,B):
     * if MBR of A contains B, return 1 else 0
     * @param searchStoreInRadiusDto Current User's Location and demanded distance
     *
     * @return
     * if null, return 404 (NOT FOUND)
     * if there are no stores, return 204 (No Content)
     * if there are stores, return 200 (StoreList)
     */
    public Set<StoreDto> searchStoreInRadius(SearchStoreInRadiusDto searchStoreInRadiusDto) {
        log.debug("Start Searching Stores around = {}", searchStoreInRadiusDto);

        // 1:
        Point northEast = GeometryUtil.calculate(
                searchStoreInRadiusDto.getLatitude(),
                searchStoreInRadiusDto.getLongitude(),
                searchStoreInRadiusDto.getDistance(),
                Direction.NORTHEAST.getBearing()
        );
        Point southWest = GeometryUtil.calculate(
                searchStoreInRadiusDto.getLatitude(),
                searchStoreInRadiusDto.getLongitude(),
                searchStoreInRadiusDto.getDistance(),
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

        searchStoreInRadiusDto.setLineString(lineString);

        log.debug("Created LineString = {}", lineString);

        Optional<Set<StoreDto>> stores = storeRepository.searchStoreInRadius(searchStoreInRadiusDto);

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
        Optional<Set<StoreDto>> stores = storeRepository.searchByName(name);
        if(stores.isEmpty()) throw new NotFoundException();
        if(stores.get().isEmpty()) throw new NoContentException();
        return stores.get();
    }

    public StoreDto searchByRegistrationNumber(String registrationNumber) {
        return storeRepository.searchByRegistrationNumber(registrationNumber);
    }
}
