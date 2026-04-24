# AGENTS.md

## Project Overview

Weekly Menu is a Java/Spring backend for building shopping lists from recipes. The app stores inventory products, categories, recipes, and carts. A recipe contains product details with quantities; a cart/shopping list contains selected products and the recipes that caused those products to be included.

The codebase follows a lightweight Clean Architecture style:

- `useCase`: framework-free domain entities, gateway interfaces, validators, and interactors.
- `springApp`: Spring Boot application, REST controllers, JPA entities/repositories, gateway implementations, mapper implementations, and tests.

The public REST API is versioned under `/v1`.

## Current Technical Baseline

- Java: 21
- Build: Maven multi-module project
- Spring Boot: 3.x
- Persistence: Spring Data JPA with PostgreSQL in main config and H2 in test config
- OpenAPI UI: springdoc Boot 3 starter
- Lombok is used throughout entities and models

Run the test suite from the repo root:

```bash
sh mvnw test
```

The wrapper script may not be executable, so prefer `sh mvnw ...` unless permissions have been fixed.

## Module Layout

Root files:

- `pom.xml`: parent Maven project and shared dependency management.
- `.mvn/wrapper/maven-wrapper.properties`: Maven wrapper distribution.
- `.github/workflows/maven.yml`: CI build.

Use-case module:

- `useCase/src/main/java/com/weeklyMenu/useCase/entity`: domain objects such as `Product`, `Category`, `Recipe`, `ProdDetail`, `Cart`, and `CartItem`.
- `useCase/src/main/java/com/weeklyMenu/useCase/gateway`: gateway interfaces implemented by adapters.
- `useCase/src/main/java/com/weeklyMenu/useCase/Interactor`: application use cases such as find/manage product, category, recipe, and cart.
- `useCase/src/main/java/com/weeklyMenu/useCase/Interactor/validator`: validation logic.

Spring app module:

- `springApp/src/main/java/com/weeklyMenu/WeeklyMenuApplication.java`: Spring Boot entrypoint.
- `springApp/src/main/java/com/weeklyMenu/adaptor/controller`: REST controllers.
- `springApp/src/main/java/com/weeklyMenu/adaptor/model`: JPA entities.
- `springApp/src/main/java/com/weeklyMenu/adaptor/springData`: Spring Data repositories.
- `springApp/src/main/java/com/weeklyMenu/adaptor/gateway`: gateway implementations that bridge use cases to repositories.
- `springApp/src/main/java/com/weeklyMenu/adaptor/mapper`: hand-written mapper implementations.
- `springApp/src/main/resources/application.yml`: local PostgreSQL config.
- `springApp/src/test/resources/application.yml`: H2 test config.
- `springApp/src/test/http-client`: IntelliJ HTTP client examples.

## API Shape

Base URL: `/v1`

Main resources:

- `GET /v1/products`
- `GET /v1/products/{id}`
- `POST /v1/products`
- `PUT /v1/products`
- `DELETE /v1/products/{id}`
- `GET /v1/categories`
- `GET /v1/categories/{id}`
- `POST /v1/categories`
- `PUT /v1/categories`
- `DELETE /v1/categories/{id}`
- `GET /v1/recipes`
- `GET /v1/recipes/{id}`
- `POST /v1/recipes`
- `PUT /v1/recipes`
- `DELETE /v1/recipes/{id}`
- `GET /v1/carts`
- `POST /v1/carts`
- `PUT /v1/carts`
- `DELETE /v1/carts/{id}`

## Important Domain Notes

- Products belong to categories by `catId`.
- Recipes contain `prodsDetail`, each item linking to a product by `prodId` and a quantity.
- Carts contain `cartItems`.
- Cart items reference products with a many-to-one JPA relationship. Do not restore the previous one-to-one unique mapping; cart items need to be able to reference the same inventory product across different carts/items.
- Cart item `recipes` is a set of recipe IDs.
- `BasicEntity` is embedded in JPA models for creation/update/status metadata. Builders should preserve this default with `@Builder.Default`.

## Coding Conventions And Cautions

- Keep `useCase` free of Spring/JPA dependencies.
- Prefer existing hand-written mapper style unless intentionally introducing a mapper framework.
- The package currently uses `adaptor` and uppercase `Interactor`; this is historical. Avoid broad package renames unless that is the explicit task.
- Do not reintroduce `javax.persistence` or `javax.validation`; Spring Boot 3 uses `jakarta.*`.
- Avoid cascade delete from cart items to products. Products are inventory records, not cart-owned children.
- Several old test files are fully commented out and reference stale package names. Treat them as historical notes unless explicitly reviving them.
- `target/`, IDE files, and build artifacts are ignored. Do not commit generated build output.

## Verification Checklist

For backend changes, run:

```bash
sh mvnw test
```

For API payload changes, also review/update:

```text
springApp/src/test/http-client/*.http
```

When touching JPA mappings, add or update focused tests under `springApp/src/test/java/com/weeklyMenu/adaptor/model` or related gateway tests.

## Suggested Phase 6: Test Coverage

The project has a passing smoke/regression suite, but not meaningful coverage yet. Phase 6 should focus on establishing coverage visibility and replacing stale commented tests with active Spring Boot 3/JUnit 5 tests.

Recommended scope:

- Add JaCoCo to Maven.
- Generate a coverage report during `sh mvnw test` or `sh mvnw verify`.
- Add a modest initial coverage threshold only after the current baseline is visible.
- Restore or rewrite the old commented controller/data tests instead of uncommenting them directly; many reference stale packages and JUnit 4 APIs.
- Add Spring Boot context startup coverage.
- Add HTTP/controller integration tests for products, categories, recipes, and carts.
- Add use-case validation tests for duplicate names, missing product/category IDs, and recipe/cart validation failures.
- Add persistence tests for recipe create/update and cart create/update behavior.

Current active suite covers mapper behavior, delete endpoint mappings, recipe gateway lookup, and the cart item product relationship. It does not yet cover the main user workflows end to end.
