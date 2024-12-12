# Find-My-Doc
Поиск копий документов по переданным оригинальным изображениям

```
Find-my-doc/
├── backend/
│   ├── api-gateway/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/
│   │   │   │   │   └── ru/
│   │   │   │   │       └── aidoc/
│   │   │   │   │           └── apigateway/
│   │   │   │   │               └── utils/
│   │   │   │   │                   └── JsonUtil.java
│   │   │   │   └── resources/
│   │   ├── pom.xml
│   ├── auth-service/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/
│   │   │   │   │   └── ru/
│   │   │   │   │       └── aibok/
│   │   │   │   │           └── auth/
│   │   │   │   │               └── AuthServerApplication.java
│   │   │   │   └── resources/
│   │   ├── pom.xml
│   ├── common/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/
│   │   │   │   │   └── ru/
│   │   │   │   │       └── aidoc/
│   │   │   │   │           └── common/
│   │   │   │   └── resources/
│   │   ├── pom.xml
│   ├── pom.xml
├── docker/
│   ├── api-gateway/
│   │   └── Dockerfile
│   ├── auth-service/
│   │   └── Dockerfile
│   ├── common/
│   │   └── Dockerfile
│   ├── doc-service/
│   │   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── utils/
│   │   └── styles/
│   ├── public/
│   ├── package.json
│   └── webpack.config.js
├── kubernetes/
│   ├── api-gateway/
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   └── secret.yaml
│   ├── auth-server/
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   └── secret.yaml
│   ├── postgres/
│   │   ├── configmap-db-init-scripts.yaml
│   │   ├── postgres-pvc.yaml
│   │   ├── postgres-deployment.yaml
│   │   └── postgres-service.yaml
├── documentation/
│   ├── architecture.md
│   ├── kuber.md
│   ├── backend.md
│   ├── ocr.md
│   ├── kafka.md
│   ├── development-guidelines.md
│   └── semantic-analysis.md
├── docker-compose.yml
├── Makefile
└── README.md
```
