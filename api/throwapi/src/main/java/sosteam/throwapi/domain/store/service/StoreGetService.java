package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.exception.NoContentException;
import sosteam.throwapi.domain.store.exception.NotFoundException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.Direction;
import sosteam.throwapi.domain.store.util.GeometryUtil;

import java.util.List;
import java.util.Optional;

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
    public List<StoreResponseDto> search(SearchStoreInRadiusDto searchStoreInRadiusDto) {
        log.info("Start Searching Stores around = {}", searchStoreInRadiusDto);

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
        log.info(String.format("NorthEast(%s %s) SRID:%s", northEast.getX(), northEast.getY(), northEast.getSRID()));
        log.info(String.format("SouthWest(%s %s) SRID:%s", southWest.getX(), southWest.getY(), southWest.getSRID()));
        GeometryFactory geometryFactory = GeometryUtil.getGeometryFactory();
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(northEast.getY(), northEast.getX()),
                new Coordinate(southWest.getY(), southWest.getX())
        };

        LineString lineString = geometryFactory.createLineString(coordinates);

        searchStoreInRadiusDto.setLineString(lineString);

        log.info("Created LineString = {}", lineString);

        Optional<List<StoreResponseDto>> storeListOptional = storeRepository.search(searchStoreInRadiusDto);

        if(storeListOptional.isEmpty()) throw new NotFoundException();
        if(storeListOptional.get().isEmpty()) throw new NoContentException();
        return storeListOptional.get();
    }


    public StoreResponseDto findByRegistrationNumber(String registrationNumber) {
        return storeRepository.findByRegistrationNumber(registrationNumber);
    }
}
