# Развертывание сервиса в Minikube

## Требования

1. **Минимум два Deployment**, соответствующих количеству сервисов.
2. **Кастомный образ** для минимум одного Deployment (не публичный, собранный из собственного Dockerfile).
3. Один из Deployment:
   - Содержит основной контейнер и **init-контейнер**.
   - Использует **volume** (любой тип).
4. Использование **ConfigMap** и/или **Secret**.
5. Наличие **Service** хотя бы для одного сервиса.
6. Реализация **Liveness** и/или **Readiness** проб в одном из Deployment.
7. Применение **лейблов** (включая `selector/matchLabel`).

## Структура проекта

```plaintext
Find-my-doc/ 
├── backend/
│   ├── common/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/
│   │   │   │   │   └── ru/aidoc/common/
│   │   │   │   │       ├── config/           # Общие конфигурации
│   │   │   │   │       ├── dto/              # Общие DTO
│   │   │   │   │       ├── entity/           # Общие сущности
│   │   │   │   │       ├── exceptions/       # Кастомные исключения
│   │   │   │   │       ├── utils/            # Утилиты
│   │   │   │   │       └── security/         # Безопасность
│   │   │   └── resources/
│   │   │       ├── application.yml          # Базовая конфигурация
│   │   │       └── logback.xml              # Конфигурация логирования
│   │   └── test/
│   │       ├── MockDataGenerator.java       # Генерация тестовых данных
│   │       └── CommonTestUtils.java         # Утилиты для тестов
│   └── pom.xml
│
│   ├── api-gateway/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   └── java/
│   │   │   │       └── ru/aidoc/apigateway/
│   │   │   │           ├── config/
│   │   │   │           ├── controller/
│   │   │   │           ├── service/
│   │   │   │           └── model/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
│   ├── auth-service/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   └── java/
│   │   │   │       └── ru/aidoc/authservice/
│   │   │   │           ├── config/
│   │   │   │           ├── controller/
│   │   │   │           ├── service/
│   │   │   │           └── model/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
│   ├── doc-service/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   └── java/
│   │   │   │       └── ru/aidoc/docservice/
│   │   │   │           ├── config/
│   │   │   │           ├── controller/
│   │   │   │           ├── service/
│   │   │   │           └── model/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
└── docker/
    ├── common/
    │   └── Dockerfile
    ├── api-gateway/
    │   └── Dockerfile
    ├── auth-service/
    │   └── Dockerfile
    └── doc-service/
        └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── api/                 # API клиенты
│   │   ├── components/          # React компоненты
│   │   ├── pages/               # Страницы приложения
│   │   ├── services/            # Сервисы
│   │   ├── utils/               # Утилиты
│   │   └── styles/              # CSS стили
│   ├── public/                  # Статические файлы
│   ├── package.json
│   └── webpack.config.js
├── docker-compose.yml           # В корне проекта
```
# С кубером
```
Find-my-doc/
├── backend/
│   ├── api-gateway/
│   ├── auth-service/
│   ├── common/
│   └── doc-service/
├── docker/
├── frontend/
├── kubernetes/
│   ├── api-gateway/
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   └── secret.yaml
│   ├── auth-server/
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   └── secret.yaml
│   └── ... # Other services
└── docker-compose.yml

```