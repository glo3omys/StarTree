# StarTree

식물의 사진을 통해 식물병 5가지와 정상상태를 진단 및 해결방안을 제공하는 애플리케이션


## 프로젝트 소개

집안에서 다양한 식물을 키우는 가구가 증가함에 따라 실내 식물의 정보 및 관리 방법에 대한 수요가 증가하고 있습니다. 하지만 식물을 처음 키우거나 식물에 대해 잘 모르는 사람들의 경우 식물의 상태를 잘 파악하지 못하고 식물이 병에 걸렸을 때 식물을 키우는 것을 포기하는 경우도 있습니다. 만약, 자신이 키우는 식물이 어떤 병인지를 파악하고 해결방안을 알 수 있다면 지속적으로 식물을 기르는 것에 관심을 가질 수 있을 것입니다.

Star Tree는 식물의 건강 상태를 파악하고, 질병을 진단하고 해결하는 데 도움을 주는 애플리케이션입니다. 사용자는 식물의 사진을 업로드하면 식물병 5가지와 정상상태를 분류하는 학습된 딥러닝 모델을 통해 식물의 질병 여부를 빠르게 판단하고 해결방안을 제공받을 수 있습니다.


## 시연 영상

[![Video Label](http://img.youtube.com/vi/gwRDJs_bvi8/0.jpg)](https://youtu.be/gwRDJs_bvi8)


## 개발 기간

개발 기간: 2023-09-13 ~ 2023-12-12


## 개발 환경

- OS : Window 10, Window 11
- 개발 도구 : Android Studio, Google Colab(Google Drive), Figma, PyCharm
- 개발 언어 : Python, Kotlin
- 사용한 라이브러리 (Python) : PyTorch, PIL, FastAPI, os, shutil, cv2, albumentations, pandas, torch, torchvision, torchsummary
- 사용한 라이브러리 (Kotlin) : Gson, Dagger Hilt, Room Database, TedPermission, Retrofit
DB : SQLite


## 시스템 구조

-  애플리케이션 : 사용자는 안드로이드 환경에서 애플리케이션을 통해 서비스를 이용합니다. 사용자의 요청은 FastAPI 서버로 전달되고 애플리케이션은 서버로부터 예측값을 받습니다.
-  FastAPI 서버 : 애플리케이션으로부터 받은 요청을 모델에 전달하고, 결과를 애플리케이션 으로 다시 전달합니다.
-  모델 : Google Colab 환경에서 Pytorch를 이용하여 설계 및 훈련되었습니다. FastAPI 서버에 배포되어 사용자 요청에 대한 예측을 제공합니다.


## 주요 기능 및 스크린샷

- 핸드폰에 저장된 이미지 파일을 통해 질병 여부 진단
  
![star_tree_1](https://github.com/glo3omys/StarTree/assets/36217363/15b407e9-4224-43ef-8f56-70fc69ae6b87)

- 진단 결과를 저장해 두고 파악 가능
  
![star_tree_2](https://github.com/glo3omys/StarTree/assets/36217363/a52fc07a-d80d-40b7-b556-3164452fc0d1)


## 향후 추가할 기능

- SNS 계정 연동: 카카오톡, 구글 등의 계정을 이용해 로그인을 할 수 있습니다.
- 식물별로 진단 내역 관리: 여러 식물을 키우는 사용자의 경우, 식물을 각각 관리할 수 있도록 합니다.
- 사용자 게시판 및 커뮤니티: 사용자들이 소통할 수 있는 페이지 입니다.
- 코루틴: 서버 통신 및 비동기 작업 등에 적용합니다.


## 참고 자료

- bioadvanced, “Signs of Disease in Common Houseplants”, https://bioadvanced.com/signs-common-houseplant-diseases, 2023.09.23	
- Kaggle, “new-plant-diseases-dataset”, https://www.kaggle.com/datasets/vipoooool/new-plant-diseases-dataset, 2023.09.24

