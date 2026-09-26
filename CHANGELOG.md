# Changelog

## 2.1.0 - 2026-09-26

- [minor] KAN-106 aplica la estrategia común de errores Kafka de KAN-18.
- [minor] Separa errores permanentes de CSV/ruta de fallos transitorios de Kafka.
- [minor] Configura retries/backoff y DLT `file.ready.match-summary.DLT`.
- [minor] Añade tests de clasificación de error permanente y transitorio.


## 2.0.7 - 2026-09-25

- [patch] KAN-80 sustituye los schemas locales FileEvent/StatsMatch por `basketball-event-contracts:1.0.2`.
- [patch] Elimina generación Avro local y configura CI con lectura autenticada de GitHub Packages.
- [patch] Mantiene sin cambios el parsing MATCH_SUMMARY y la publicación a Kafka.


## 2.0.6 - 2026-09-25

- [patch] KAN-69 valida MATCH_SUMMARY contra `CSV_ALLOWED_ROOT` antes de abrir el CSV.
- [patch] Usa rutas reales para bloquear escapes mediante symlink y rechaza rutas relativas o ficheros inexistentes.
- [patch] Añade tests de seguridad de filesystem.

## 2.0.5 - 2026-09-25

- [patch] Refuerza AGENTS.md con lectura obligatoria por tarea, autonomía y prohibición absoluta de escrituras directas en main.

## 2.0.4

- [patch] Estandariza la automatización del repositorio con el flujo autónomo de csv-results-parser.

## 2.0.3 - 2026-09-25

- [patch] Añade eliminación automática de la rama origen después de mergear una Pull Request en `main`.

## 2.0.2 - 2026-09-25

- [patch] Exige confirmar rama y nivel SemVer antes de cualquier cambio.
- [patch] Alinea Maven CI-friendly con revision, sha1 y changelist.

## 2.0.0 - 2026-09-24
- Consolida como contrato MAJOR la arquitectura parser Kafka sin PostgreSQL.
- Mantiene la publicación Avro en `stats-match.parsed` y documenta el gobierno común del repositorio.
- Alinea versión, README, CHANGELOG, AGENTS.md y CI con el resto de microservicios CSV.

## 1.0.1 - 2026-09-24
- Simplifica el contrato intermedio: el parser publica solo resúmenes válidos.
- Elimina los mensajes `FAILED` y metadatos que persistence no necesita.

## 1.0.0 - 2026-09-24
- Separa el parser de MATCH_SUMMARY.
- Publica en `stats-match.parsed`.
- Elimina JPA, PostgreSQL y Flyway.
- Añade auto-merge tras checks y borrado de rama.
