# DB 세팅
*django-environ을 활용한 환경변수 분리, TIME_ZONE과 언어 설정에 따른 settings.py를 수정한다.*

## 준비
1. environ 패키지 설치
2. .env 파일 생성
3. settings.py 파일 수정

<br><hr><br>

## environ 패키지 설치
- [django-environ](https://django-environ.readthedocs.io/en/latest/install.html) 을 설치한다.
```shell
python -m pip install django-environ
```

<br><hr><br>

## .env 파일 생성
- 환경 변수로 지정할 값들을 분리한다.
```text
SECRETE_KEY=[Django의 SECRETE_KEY]
DB_NAME=[DB NAME]
DB_USER=[DB USER NAME]
DB_PASSWORD=[PASSWORD]
DB_PORT=[PORT]
```
>예시
```text
SECRETE_KEY=django-insecure-7c8h6hb__kt4q-=hzq)#z57*f$y@0^7@au_fuip!3!o8niwh&q
DB_NAME=test_db
DB_USER=test_user
DB_PASSWORD=test
DB_PORT=3306
```

<br><hr><br>

## settings.py 수정
```python
import environ

env = environ.Env(
    # set casting, default value
    DEBUG=(bool, False),
    SECRET_KEY=(str, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')
)

# Set the project base directory
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Take environment variables from .env file
environ.Env.read_env(os.path.join(BASE_DIR, '.env'))

SECRET_KEY = env('SECRET_KEY')

# TEMPLATES의 DIRS 부분이 다른 경우가 경로를 맞춰준다.
TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [os.path.join(BASE_DIR, 'templates')],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]


DATABASES = {
    "default": {
        "ENGINE": "django.db.backends.mysql",
        "NAME": env("DB_NAME"),
        "USER": env("DB_USER"),
        "PASSWORD": env("DB_PASSWORD"),
        "HOST": "localhost",
        "PORT": env("DB_PORT"),
    }
}

# 언어와 TIME_ZONE 수정
LANGUAGE_CODE = 'ko-kr'

TIME_ZONE = 'Asia/Seoul'

USE_I18N = True

USE_TZ = True
```