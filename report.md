# Assignment 1: Builder Pattern — Cloud Deployment

* **Student:** Nurakhmet Muktiyar
* **Course:** Software Design Patterns (PhD Makpal Zhartybayeva)[cite: 1]
* **Domain:** Cloud Deployment[cite: 1]
* **Constraint:** PRODUCTION requires monitoring, backup, and >= 8192 MB RAM[cite: 1]

---

## Part A — Design Problem
Telescoping constructors lead to anti-patterns:
`new CloudDeployment("service", "PRODUCTION", "us-east-1", 4, 8192, 100, true, true, "cert.pem");`

**Problems:**
1. **Poor Readability:** Boolean sequences (`true, true`) obscure intent[cite: 1].
2. **Maintenance Issues:** Overloaded constructors scale poorly[cite: 1].
3. **Fragile Validation:** Validation breaks across optional parameters[cite: 1].

---

## Part B & C — Builder & Validation
Using fluent API `CloudDeployment.Builder`:

* **Single-Field Rules:** `serviceName` not blank; `cpu` > 0; `memoryMb` >= 512[cite: 1].
* **Cross-Field Rules:** `PRODUCTION` demands monitoring, backup, RAM >= 8192 MB; `sslCertificate` needs `.pem` format[cite: 1].

---

## Part D — Presets (Director)
* **DEVELOPMENT:** 1 CPU, 1024 MB RAM, 10 GB Storage[cite: 1].
* **TESTING:** 2 CPU, 4096 MB RAM, 40 GB Storage + Monitoring[cite: 1].
* **PRODUCTION:** 8 CPU, 16384 MB RAM, 250 GB Storage + SSL + Backup + Monitoring (Outputs 🍌)[cite: 1].

---

## Part E — Clean Code

| Principle | Before | After | Improvement |
| :--- | :--- | :--- | :--- |
| **No Flag Args** | `setOptions(true, false)` | `enableMonitoring().withAutomaticBackup()` | Expresses intent without booleans[cite: 1]. |
| **Domain Naming** | `setRegion("us-east-1")` | `deployToRegion("us-east-1")` | Business-oriented DSL[cite: 1]. |
| **SLAP** | Monolithic `build()` | `validateSingleFields()` & `validateCrossFields()` | Enforces Single Responsibility[cite: 1]. |

---

## Part F — Design Decision
* **Decision:** Perform validation in `Builder.build()` before object creation[cite: 1].
* **Alternative:** Validating in `CloudDeployment` constructor[cite: 1].
* **Reasoning:** Builder guarantees invalid state never reaches the immutable product[cite: 1].

---

## Part G — UML Matrix

| Role | Class | Responsibility |
| :--- | :--- | :--- |
| **Product** | `CloudDeployment` | Immutable deployment entity[cite: 1]. |
| **Builder** | `CloudDeployment.Builder` | Fluent setup & validation[cite: 1]. |
| **Director** | `DeploymentDirector` | Preset configurations[cite: 1]. |
| **Value Object** | `ResourceLimits` | Immutable CPU allocation bounds[cite: 1]. |
| **Client** | `Main` / `CloudDeploymentTest` | Entry points[cite: 1]. |

---

## Part H — Automated Testing
Executed 10 JUnit 5 tests covering 3 valid, 3 invalid, 2 boundary cases, 1 constraint rule, and 1 immutability check[cite: 1].

**Console Output:**
```text
Deploying Production Environment... 🍌
Prod Memory: 16384 MB
Process finished with exit code 0