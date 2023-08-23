# Thorw
Throw Application

# 지구 위에서 두 점 사이 거리 구하는 방법
- 하버사인 공식을 이용
- https://kayuse88.github.io/haversine/

```java
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

```

# Point, Geometry, LineString (domain/store/util)
- `Gemortry` 와 `GeometryFacotory`사용
- Geometry에는 double 두 개를 입력 받아 WellKnownText로 변환 후 wktReader를 사용해서 POINT 타입 객체를 생성
- GeometryFacotry는 LineString을 생성하기 위해 사용
