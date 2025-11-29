# 🧾 Kafka Microservices Lab

### Event-Driven Order Processing with Spring Boot, Kafka, MongoDB & PostgreSQL

This project demonstrates an end-to-end **event-driven microservices architecture** using:

* **Order Service** — handles order creation & publishes Kafka events
* **Inventory Service** — updates stock based on order events
* **Kafka Broker & Zookeeper** — message backbone
* **PostgreSQL** — stores orders
* **MongoDB** — stores inventory

The services communicate asynchronously using **Kafka topics**.

---

## 🏗️ Architecture Overview

```
 ┌────────────────────┐      order-events       ┌────────────────────┐
 │   ORDER-SERVICE     │ ─────────────────────→ │ INVENTORY-SERVICE   │
 │  (Postgres + Kafka) │                          │ (MongoDB + Kafka)  │
 └──────────┬──────────┘                          └──────────┬────────┘
            │                                             │
            │ order-status-events                         │
            └─────────────────────────────────────────────┘
```

**Flow:**

1. Client sends `POST /api/orders`
2. Order-Service stores order → publishes **OrderPlacedEvent**
3. Inventory-Service consumes event → reduces stock → publishes **OrderStatusEvent**
4. Order-Service consumes status → updates order status in DB to `COMPLETED` or `REJECTED`

---

## 📦 Services

| Service           | Port      | DB         | Description                               |
| ----------------- | --------- | ---------- | ----------------------------------------- |
| order-service     | **8081**  | PostgreSQL | Creates orders and publishes Kafka events |
| inventory-service | **8082**  | MongoDB    | Consumes order events and updates stock   |
| Kafka broker      | **9092**  | —          | Event bus                                 |
| Zookeeper         | **2181**  | —          | Kafka coordinator                         |
| Postgres          | **5433**  | orderdb    | Stores orders                             |
| MongoDB           | **27018** | inventory  | Stores inventory                          |

---

## 🐳 Run Everything with Docker

```bash
docker compose up -d --build
```

### Check running containers

```bash
docker ps
```

---

## 🚀 End-to-End Test

### 1️⃣ Create order (Postman / curl)

```
POST http://localhost:8081/api/orders
Content-Type: application/json

{
  "skuCode": "iphone15",
  "quantity": 1,
  "price": 1200.0,
  "customerEmail": "me@mail.com"
}
```

**Expected Output**:

```json
{
  "id": 5,
  "skuCode": "iphone15",
  "quantity": 1,
  "price": 1200.0,
  "customerEmail": "me@mail.com",
  "status": "COMPLETED",
  "createdAt": "2025-11-29T21:58:23.0167791"
}
```

### 2️⃣ Verify inventory

```bash
docker exec -it mongodb-inventory mongosh -u admin -p admin
use inventory
db.inventoryItem.find()
```

➡️ Quantity should decrement

---

## 🧪 Kafka Topics

| Topic                 | Producer          | Consumer          |
| --------------------- | ----------------- | ----------------- |
| `order-events`        | order-service     | inventory-service |
| `order-status-events` | inventory-service | order-service     |

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Kafka**
* **Kafka / Zookeeper**
* **PostgreSQL + JPA / Hibernate**
* **MongoDB**
* **Docker Compose**

---

## 📁 Repository Structure

```
kafka-microservices/
├── docker-compose.yml
├── order-service/
│   ├── src/main/java/com/lab/order/...
│   ├── application.yml
│   └── Dockerfile
└── inventory-service/
    ├── src/main/java/com/lab/inventory/...
    ├── application.yml
    └── Dockerfile
```

---