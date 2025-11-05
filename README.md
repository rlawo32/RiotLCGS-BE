
## 🏆 Riot LCGS(League of Legends Custom Game Save) - Back

리그오브레전드의 커스텀 게임 전적 저장 서비스

<br/>

## 📖 LCG 프로젝트 : LCGS-BE 소개

LCG 프로젝트는 기존 리그오브레전드 전적 사이트에서 커스텀 게임에 대한 기록 확인이 불가하기에 직접 내가 했던 커스텀 게임들을 기록으로 남기고 전적 사이트처럼 구성하고자 시작하게 되었습니다.

LCG 프로젝트 중 LCGS-BE는 LCGS-FE에서 전달받은 데이터와 DataDragon API를 통해 가져온 JSON 데이터들을 기반으로 데이터를 가공하여 SUPABASE에 INSERT 및 UPDATE 작업을 담당하고 있습니다. <br/>
LCGS-FE에서 전달되는 데이터는 간단한 정보 Code 값만 담겨 있기에 View 구성 및 정확한 데이터 확인이 불가하여 Code 값들을 확인할 수 있는 DataDragon의 JSON 데이터와 매핑하여 값을 추출한 뒤 SUPABASE로 전달하는 과정으로 이루어져 있습니다.

데이터 저장은 SUPABASE와 연동하여 구현 하였으며, JPA와 QueryDSL을 사용하여 저장 작업을 진행 하였습니다. 또한, LCU API에서 가져온 데이터를 DTO 형태로 받아 사용하기 위해 API JSON 데이터를 토대로 DTO를 생성하고 필요하지만 LCU에서 제공되지 않는 데이터 부분은 임의로 생성하여 해결하였습니다.

이외에도 정해진 데이터 값을 토대로 제가 별도로 생성한 각 게임별 플레이어 MVP 및 순위 도출 로직이나 전달되는 데이터 유효성 검사 로직 등의 기능도 구성하였습니다.

<br/>

## 📅 개발 기간

+ `2024. 12. 8. ~ ing`

<br/>

## 📋 구현 목록

+ LCGS-FE 통신 테스트 ✅ <br/>
+ SUPABASE 연결 및 SAVE 테스트 ✅ <br/>
+ DataDragon API 통신 테스트 ✅ <br/>
+ LCU API 데이터 및 DataDragon JSON 데이터 매핑/추출 작업 ✅ <br/>
+ 데이터 항목별 DTO 생성 및 DTO 매핑 ✅ <br/>
+ SUPABASE 테이블 생성(13개) ✅ <br/>
+ QueryDSL 쿼리 작업 ✅ <br/>
+ MVP 도출 및 전체 랭킹 계산 ✅ <br/>
+ DPM, GPM 등 추가 데이터 계산 및 각 항목별 MAX 데이터 추출 ✅ <br/>
+ Electron 앱 개발 (LCGS-FE + LCGS-BE) 🚧 <br/>

<br/>

## 🛠️ 사용 툴, 언어

+ IntelliJ
+ Java
+ JPA
+ SUPABASE
+ QueryDSL

<br/>

## 🔗 참고 사이트

+ SUPABASE - [SUPABASE Docs](https://supabase.com/docs/reference/javascript/introduction)
+ DDRAGON - [Riot Data Dragon](https://developer.riotgames.com/docs/lol)
