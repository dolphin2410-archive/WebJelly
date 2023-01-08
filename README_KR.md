# WebJelly
### 런타임에 maven artifact 불러오기

- [English](README.md)

WebJelly는 `MavenCentral` 저장소 이외의 외부 저장소에서 라이브러리를 불러올 수 있게 해주는 플러그인입니다.

## 문제
예를 들어 다음과 같은 저장소가 있다고 합시다.
```text
https://example.com/repo/
```
위 저장소에 있는 `myproject` 라이브러리를 불러오고 싶지만, 스피갓은 라이브러리를 `MavenCentral` 저장소에서만 불러오기 때문에, 다음과 같이 할 수는 없습니다.
```yaml
name: MyPlugin
main: io.github.xxx.plugin.MyPlugin
version: 1.0
libraries:
  - com.example.myproject:myproject:1.0
```

## 해결
여러분이 알듯이, 스피갓 서버의 코드를 수정해 `maven-central`에서만이 아닌 자신이 원하는 저장소에서도 불러올 수 있게 만들는 것은 불가능합니다. 따라서 저는 런타임에 원하는 라이브러리를 불러와주는 플러그인 로더를 만들었습니다.
### webjelly.json
`webjelly.json`는 소스코드의 `resources` 폴더 안에 있어야합니다. 이는 `jar` 파일 생성시 루트에 위치해야하며, 다음과 같이 사용할 수 있습니다.
```json
{
  "<repository>": [
    "artifact1",
    "artifact2"
  ],
  "<another_repository>": [
    "artifact3"
  ]
}
```

So in the case when you want to load `myproject` from `https://example.com/repo/`, you can use
```json
{
  "https://example.com/repo/": [
    "myproject"
  ]
}
```


MavenCentral과 MavenLocal도 지원됩니다

```json
{
    "mavenLocal": [
        "myproject"
    ],
    "mavenCentral": [
        "myproject2"
    ]
}
```

### 플러그인 사용하기
파일 구조는 다음과 같아야합니다.
```bash
├── plugins
│   ├── webjelly
│   │   └── myplugin.jar
│   └── webjelly.jar
├── ...
```
***여러분이 만든 플러그인은 plugins 폴더가 아닌 plugin 폴더 내부에 있는 webjelly 폴더에 넣어야합니다. webjelly 폴더가 존재하지 않는 경우 생성하세요.***