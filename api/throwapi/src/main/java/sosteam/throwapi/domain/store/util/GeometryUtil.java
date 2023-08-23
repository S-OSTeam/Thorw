package sosteam.throwapi.domain.store.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Slf4j
public class GeometryUtil {
    /**
     * Spatial Reference Identifier : 공간 참조 식별자
     * 데이터의 지리적 좌표계와 투영법에 대한 정보를 구분하는 식별 코드
     * 즉 SRID 값에 따라 위도, 경도를 표현하는 방법이 다르다
     * 4326은 WGS84로, 세계 지구 좌표 시스템이다.
     */
    public static final int SRID = 4326;

    @Getter
    public static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final WKTReader wktReader = new WKTReader();

    /**
     * 텍스트를 Geometry 유형으로 변환하는 메소드
     * @param wellKnownText (ex) POINT(4 3)
     * @return Geometry 객체
     */
    private static Geometry wktToGeometry(String wellKnownText) {
        Geometry geometry = null;
        try {
            geometry = wktReader.read(wellKnownText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.debug("Created Geometry : {}", geometry);
        return geometry;
    }

    /**
     * (경도,위도)를 입력 받아 POINT 타입 객체 반환
     * @param x longitude 경도
     * @param y latitude 위도
     * @return POINT(x,y)
     */
    public static Point parseLocation(double x, double y) {
        Geometry geometry = GeometryUtil.wktToGeometry(String.format("POINT(%s %s)", x, y));
        Point point = (Point) geometry;
        point.setSRID(SRID);
        return point;
    }

    /**
     *
     * @param baseLatitude 현재 위치 경도
     * @param baseLongitude 현재 위치 위도
     * @param distance 탐색 반경 거리
     * @param bearing 방향(복,북동,동...,서북) 8개 중 택 1
     * @return 현재 위치에서 bearing 방향으로 distance만큼 떨어져있는 POINT
     */
    public static Point calculate(Double baseLatitude, Double baseLongitude, Double distance,
                                  Double bearing) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
                cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
                cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

        longitude = normalizeLongitude(longitude);
        return parseLocation(toDegree(latitude), toDegree(longitude));
    }


    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    private static Double normalizeLongitude(Double longitude) {
        return (longitude + 540) % 360 - 180;
    }
}
