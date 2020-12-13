# 상호운용성 테스트

## WebServer
- project 1과 동일

## WebClient
- project 2와 거의 동일
  - User-Agent를 input로 처리를 했다가 아래와 같이 edit함.
  ```c
  conn.setRequestProperty("User-Agent", "2019044711/CHANWOONG+KIM/WEBCLIENT/COMPUTERNETWORK");
  ```