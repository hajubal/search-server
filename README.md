# 프로젝트 설명

- JAVA 11, Spring Boot, Gradle, H2
- 멀티 모듈 구성
  - api-server: end-point 제공하는 web application
  - module-keyword: 검색어 저장, 인기 검색어 조회 모듈
  - module-search: 키워드를 통한 검색 모듈
- 검색 소스 확장성
  - 인터페이스 구현으로 새로운 블로그 검색 소스 추가
- 장애 대응
  - 검색 중 비정상 응답 수신 시 다음 검색 소스에서 데이터  조회
- 대용량 트레픽 대응
  - Spring cache 적용
- 동시성 이슈 대응
  - JPQL update query 적용
- 외부 라이브러리
  - org.apache.httpcomponents:httpclient: 외부 API 호출
  - org.springframework.boot:spring-boot-starter-cache: 대용량 데이터 검색 Cash 적용

# API 명세

## /v1/search/blog: 블로그 검색

### Parameter

| Name  | Type    | Description                                                | Required |
|-------|---------|------------------------------------------------------------|----------|
| query | String  | 검색을 원하는 질의어<br/>- 한글의 경우 UTF-8 인코딩되어야 합니다.         | O        |
| sort  | String  | 결과 문서 정렬 방식<br/>- accuracy: 정확도순(기본 값)<br/> - recency: 최신순 | X        |
| page  | Integer | 결과 페이지 번호<br/>- 1~50 사이의 값(기본 값 1)                         | X        |
| size  | Integer | 한 페이지에 보여질 문서 수<br/>- 1~50 사이의 값(기본 값 1)                   | X        |

### Response Data

| Name      | Type            | Description          |
|-----------|-----------------|----------------------|
| meta      | Meta            | 블로그 조회 메타정보 |
| documents | Array(Document) | 블로그 조회결과      |

#### Meta

| Name       | Type    | Description        |
|------------|---------|--------------------|
| totalCount | Integer | 검색된 문서 수           |
| isEnd      | Boolean | 현재 페이지가 마지막 페이지 여부 |
| sorted     | String  | 결과 문서 정렬 방식<br/>- accuracy: 정확도순<br/> - recency: 최신순              |

#### Document

| Name      | Type | Description                                         |
|-----------|------|-----------------------------------------------------|
| title     | String | 블로그 글 제목                                            |
| contents  | String | 블로그 글 요약                                            |
| url       | String | 블로그 글 URL                                           |
| blogname  | String | 블로그의 이름                                             |
| datetime  | Date | 블로그 글 작성시간, ISO 8601<br/>- ex: 2022-08-29 |

### Sample

#### Request curl

```curl
curl --location --request GET 'http://localhost:8080/v1/search/blog?query=korea&sort=accuracy&page=1&size=10'
```

#### Response JSON

```json
{
  "meta": {
    "totalCount": 2828155,
    "pageableCount": 793,
    "isEnd": false,
    "sorted": "accuracy"
  },
  "documents": [
    {
      "title": "한국 막걸리 Makgeolli of <b>Korea</b>",
      "contents": "2023.3.2 한국 막걸리 Makgeolli of <b>Korea</b> Makgeolli = Raw [unrefined] rice wine 이마트 한쪽 벽면을 꽉 채우고 있는 막걸리코너 막걸리의 인기를 짐작할 수 있다. 내가 어렸을 땐, 마트에 이렇게 많은 종류가 있었던 것 같지 않은데... 오래간만에 마트구경하다 유심히 봤는데 너무 재밌어서^^ 막걸리의 종류와...",
      "url": "http://petitechef.tistory.com/860",
      "blogname": "Cafezinho 카페징유",
      "datetime": "2023-03-02"
    },
      ...
  ]
}
```

## /v1/keywords/popular: 인기 검색어 조회

### Response Data

| Name      | Type                     | Description |
|-----------|--------------------------|-------------|
| popularKeywords | Array(popularKeyword) | 인기검색어 정보    |

#### PopularKeywords

| Name      | Type    | Description |
|-----------|---------|-------------|
| keyword   | String  | 검색어         |
| searchCount     | Integer | 검색 건수       |

### Sample

#### Request curl

```curl
curl --location --request GET 'http://localhost:8080/v1/keywords/popular'
```

#### Response JSON

```json
{
    "popularKeywords": [
        {
            "keyword": "korea",
            "searchCount": 3
        },
        {
            "keyword": "america",
            "searchCount": 2
        }
    ]
}
```

## 오류 코드

| Code | Description |
|------|-------------|
| E01  | 파라미터 오류     |
| E99  | 서버 오류       |

### Response Json 구조

| Name    | Type   | Description       |
|---------|--------|-------------------|
| code    | String | 오류 코드             |
| message | String | 오류 메시지            |


# 실행방법

``` shell
> ./gradlew clean :api-server:buildNeeded --stacktrace --info --refresh-dependencies -x test
> java -jar module-boot-api/build/libs/api-server-0.0.1-SNAPSHOT.jar
```
