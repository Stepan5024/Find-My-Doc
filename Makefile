# Переменные
IMAGE_AUTH_SERVER=auth-server:v1.0.0
IMAGE_API_GATEWAY=api-gateway:v1.0.0
KUBECTL_CONTEXT=minikube

# Логирование
LOG_PREFIX="[INFO]"

log:
	@echo "$(LOG_PREFIX) $1"

# Цели по умолчанию
.PHONY: all
all: docker-env build deploy restart status

# Настройка Docker CLI для использования Docker демона Minikube
.PHONY: docker-env
docker-env:
	@$(call log, "Настройка Docker CLI для использования Docker демона Minikube...")
	eval $$(minikube docker-env) > /dev/null 2>&1 && echo "$(LOG_PREFIX) Docker CLI настроен на Minikube."

# Сборка Docker-образов
.PHONY: build
build: docker-env build-auth-server build-api-gateway

.PHONY: build-auth-server
build-auth-server:
	@$(call log, "Сборка Docker-образа для auth-server...")
	docker build -t $(IMAGE_AUTH_SERVER) -f docker/auth-server/Dockerfile .

.PHONY: build-api-gateway
build-api-gateway:
	@$(call log, "Сборка Docker-образа для api-gateway...")
	docker build -t $(IMAGE_API_GATEWAY) -f docker/api-gateway/Dockerfile .

# Развертывание в Kubernetes
.PHONY: deploy
deploy: deploy-postgres deploy-auth-server deploy-api-gateway

.PHONY: deploy-postgres
deploy-postgres:
	@$(call log, "Развертывание Postgres...")
	kubectl apply -f kubernetes/postgres/configmap-db-init-scripts.yaml
	kubectl apply -f kubernetes/postgres/postgres-pvc.yaml
	kubectl apply -f kubernetes/postgres/postgres-deployment.yaml
	kubectl apply -f kubernetes/postgres/postgres-service.yaml

.PHONY: deploy-auth-server
deploy-auth-server:
	@$(call log, "Развертывание Auth Server...")
	kubectl apply -f kubernetes/auth-server/configmap-db-url.yaml
	kubectl apply -f kubernetes/auth-server/configmap.yaml
	kubectl apply -f kubernetes/auth-server/secret.yaml
	kubectl apply -f kubernetes/auth-server/secret-db-credentials.yaml
	kubectl apply -f kubernetes/auth-server/deployment.yaml
	kubectl apply -f kubernetes/auth-server/service.yaml

.PHONY: deploy-api-gateway
deploy-api-gateway:
	@$(call log, "Развертывание API Gateway...")
	kubectl apply -f kubernetes/api-gaterway/configmap.yaml
	kubectl apply -f kubernetes/api-gaterway/secret.yaml
	kubectl apply -f kubernetes/api-gaterway/deployment.yaml
	kubectl apply -f kubernetes/api-gaterway/service.yaml

# Перезапуск деплойментов после обновления образов
.PHONY: restart
restart:
	@$(call log, "Перезапуск деплойментов...")
	kubectl rollout restart deployment/auth-server
	kubectl rollout restart deployment/api-gateway
	kubectl rollout restart deployment/postgres

# Очистка ресурсов
.PHONY: clean
clean:
	@$(call log, "Удаление ресурсов из Kubernetes...")
	kubectl delete -f kubernetes/api-gateway/service.yaml || true
	kubectl delete -f kubernetes/api-gateway/deployment.yaml || true
	kubectl delete -f kubernetes/api-gateway/configmap.yaml || true
	kubectl delete -f kubernetes/api-gateway/secret.yaml || true
	kubectl delete -f kubernetes/auth-server/service.yaml || true
	kubectl delete -f kubernetes/auth-server/deployment.yaml || true
	kubectl delete -f kubernetes/auth-server/configmap-db-url.yaml || true
	kubectl delete -f kubernetes/auth-server/configmap.yaml || true
	kubectl delete -f kubernetes/auth-server/configmap-db-init-scripts.yaml || true
	kubectl delete -f kubernetes/auth-server/secret-db-credentials.yaml || true
	kubectl delete -f kubernetes/postgres/postgres-service.yaml || true
	kubectl delete -f kubernetes/postgres/postgres-deployment.yaml || true
	kubectl delete -f kubernetes/postgres/postgres-pvc.yaml || true

# Проверка состояния подов
.PHONY: status
status:
	@$(call log, "Получение статуса подов...")
	kubectl get pods

# Просмотр логов пода
# Использование: make logs POD=<имя_пода> CONTAINER=<имя_контейнера>
.PHONY: logs
logs:
ifndef POD
	$(error POD не задан. Используйте make logs POD=<имя_пода> CONTAINER=<имя_контейнера>)
endif
ifndef CONTAINER
	$(error CONTAINER не задан. Используйте make logs POD=<имя_пода> CONTAINER=<имя_контейнера>)
endif
	@$(call log, "Просмотр логов для Pod: $(POD), Container: $(CONTAINER)...")
	kubectl logs $(POD) -c $(CONTAINER)

# Полный цикл: настройка окружения, сборка и развертывание
.PHONY: all
all: docker-env build deploy restart
