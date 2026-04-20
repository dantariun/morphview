# MorphView

Android Clean Architecture + Multi-Module 학습 프로젝트

> 클린 아키텍처와 멀티모듈 구조를 직접 설계·구현하며 기록하는 토이 프로젝트입니다.  
> 구현 과정은 [기술 블로그 시리즈](https://velog.io/@pepperkim/Android-clean-architecture-Multi-Module-framework-%EC%A0%9C%EC%9E%91%EA%B8%B0)에서 확인할 수 있습니다.

---

## 기술 스택

| 분류 | 기술 |
|---|---|
| Language | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| Build | Gradle Convention Plugins, Version Catalog |
| Camera | CameraX 1.3.4 |
| Face Detection | Google ML Kit Face Detection 16.1.7 |
| Async | Kotlin Coroutines + Flow 1.8.1 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 (Android 16) |

---

## 모듈 구조

```
morphview/
├── app/                      # 앱 진입점 · Navigation · DI 초기화
├── presentation/             # UI Layer — Composable · ViewModel
├── domain/                   # Business Logic — UseCase · Entity · Repository Interface
├── data/                     # Data Layer — Repository 구현 · DB · Network
└── build-logic/
    └── convention/           # Convention Plugins — 빌드 설정 중앙 관리
```

### 의존성 방향

```
app ──▶ presentation ──▶ domain ◀── data
```

- `domain`은 외부 프레임워크에 의존하지 않는 순수 계층
- `presentation`과 `data`는 `domain`에만 의존 (역방향 의존 금지)

---

## Build Logic — Convention Plugins

모든 모듈의 Gradle 설정을 **Convention Plugin**으로 중앙화하여 반복 제거합니다.

```
build-logic/convention/src/main/kotlin/
├── ProjectExtensions.kt                    # VersionCatalog 접근 · SDK 버전 상수
├── AndroidApplicationConventionPlugin.kt   # :app 모듈 전용
├── AndroidLibraryConventionPlugin.kt       # 일반 라이브러리 모듈
└── AndroidLibraryComposeConventionPlugin.kt # Compose 포함 라이브러리 모듈
```

| Plugin ID | 대상 모듈 | 적용 내용 |
|---|---|---|
| `com.dantariun.buildlogic.application` | `:app` | compileSdk · minSdk · targetSdk · JVM 11 |
| `com.dantariun.buildlogic.library` | `:presentation` `:domain` `:data` | compileSdk · minSdk · JVM 11 · buildConfig 비활성화 |
| `com.dantariun.buildlogic.library.compose` | Compose 사용 모듈 | library 설정 상속 + Compose 활성화 |

**Convention Plugin 적용 전/후 비교**

```kotlin
// Before — 모든 모듈에서 반복
plugins { id("com.android.library"); id("kotlin-android") }
android {
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_11 ... }
    kotlinOptions { jvmTarget = "11" }
}

// After — 플러그인 ID 한 줄로 통일
plugins {
    id("com.dantariun.buildlogic.library")
}
```

---

## 아키텍처

```
┌──────────────────────────────────────────────────┐
│                      app                         │
│          MainActivity · Theme · Navigation       │
└───────────────────────┬──────────────────────────┘
                        │
┌───────────────────────▼──────────────────────────┐
│                  presentation                    │
│           Composable · ViewModel · UiState       │
└───────────────────────┬──────────────────────────┘
                        │
┌───────────────────────▼──────────────────────────┐
│                    domain                        │
│  DetectedFace · EyeState · HeadDirection · ...   │
│  FaceDetectionRepository · ObserveFaceUseCase    │
└───────────┬──────────────────────────────────────┘
            │
┌───────────▼──────────────────────────────────────┐
│                     data                         │
│  FaceDetectionRepositoryImpl · FaceMapper        │
│  CameraX ImageAnalyzer · ML Kit FaceDetector     │
└──────────────────────────────────────────────────┘
```

---

## 시작하기

**요구사항**

- Android Studio Meerkat (2024.3.1) 이상
- JDK 11 이상
- Android SDK 24 이상

**빌드**

```bash
# 프로젝트 클론
git clone https://github.com/dantariun/morphview.git

# Convention Plugin 컴파일 확인
./gradlew :build-logic:convention:compileKotlin

# 전체 빌드
./gradlew build

# 디버그 APK 생성
./gradlew :app:assembleDebug
```

---

## 구현 현황

| 모듈 / 기능 | 상태 |
|---|---|
| Convention Plugin (application · library · compose) | ✅ 완료 |
| Version Catalog 중앙화 | ✅ 완료 |
| app 기본 Compose + Material 3 테마 | ✅ 완료 |
| domain — Entity · Repository Interface · UseCase | ✅ 완료 |
| data — FaceDetectionRepositoryImpl · FaceMapper | ✅ 완료 |
| presentation — CameraX 프리뷰 · 얼굴 윤곽 오버레이 | 🔲 예정 |
| presentation — 눈/입/방향 상태 UI 표시 | 🔲 예정 |
| Hilt 의존성 주입 | 🔲 예정 |
| Navigation | 🔲 예정 |

---

## 블로그 시리즈

| 편                                                                                                                                                                                                                                                                | 내용 |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
| [1편](https://velog.io/@pepperkim/Android-clean-architecture-Multi-Module-framework-%EC%A0%9C%EC%9E%91%EA%B8%B0)                                                                                                                                                  | 클린 아키텍처 개념 · 기술 스택 결정 |
| [2편](https://velog.io/@pepperkim/Android-clean-architecture-Multi-Module-framework-%EC%A0%9C%EC%9E%91%EA%B8%B0-2)                                                                                                                                                | 3계층 구조 분석 (Presentation / Domain / Data) |
| [3편](https://velog.io/@pepperkim/Android-clean-architecture-Multi-Module-framework-%EC%A0%9C%EC%9E%91%EA%B8%B0-3)                                                                                                                                                | build-logic 분석 · Convention Plugin 필요성 |
| [4편](https://velog.io/@pepperkim/Android-clean-architecture-Multi-Module-framework-%EC%A0%9C%EC%9E%91%EA%B8%B0-4)                                                                                                                                                | build-logic 모듈 생성 · AGP / Kotlin 플러그인 연결 |
| [5편](https://velog.io/@pepperkim/Android-%ED%81%B4%EB%A6%B0-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EB%A9%80%ED%8B%B0%EB%AA%A8%EB%93%88-%EC%A0%9C%EC%9E%91%EA%B8%B0-5%ED%8E%B8-Convention-Plugin-%EC%A7%81%EC%A0%91-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EA%B8%B0) | Convention Plugin 구현 · 각 모듈 적용|
| [6편](https://velog.io/@pepperkim/Android-클린-아키텍처-멀티모듈-제작기-6편-Convention-Plugin-끝까지-마무리하기)                                                                                                                                                                        | Convention Plugin, 끝까지 마무리하기|
| [7편](https://velog.io/@pepperkim/Android-클린-아키텍처-멀티모듈-제작기-7편-Domain-레이어-설계)                                                                                                                                                                                      | Domain 레이어 설계|
| [8편](https://velog.io/@pepperkim/Android-클린-아키텍처-멀티모듈-제작기-8편-Data-레이어-구현-ML-Kit-연동과-Repository-구현체)                                                                                                                                                              | Data 레이어 — ML Kit 연동 · FaceDetectionRepositoryImpl 구현|

---

## License

```
Copyright 2025 dantariun

Licensed under the Apache License, Version 2.0
```
