import torchvision.transforms as trans
from PIL import Image

def preprocessing (image_file):
    image =Image.open(image_file)

    # 전처리 pipeline
    preprocess = trans.Compose([
        trans.Resize(256), # 축을 256으로 고정
        trans.CenterCrop(256),  # 중앙에서 256 * 256 크기로 자름
        trans.ToTensor(),  # tensor로 변환
        # 정규화 (RGB 채널의 평균, 표준편차)
        trans.Normalize(average=[0.485, 0.456, 0.406], std_dev=[0.229, 0.224, 0.225])
    ])

    processed_image = preprocess(image)
    return processed_image