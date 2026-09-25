![version](https://img.shields.io/badge/version-2.0.7-blue)
# csv-stats-match-parser

Parser separado de `csv-stats-match`.

```text
file.ready.match-summary -> CSV parser -> stats-match.parsed
```

Consume solo `MATCH_SUMMARY`, deriva `matchId` del nombre `MATCH_SUMMARY_<matchId>.csv`, exige exactamente una fila de 19 columnas y publica un único mensaje Avro con el resumen parseado.

No contiene JPA, Flyway ni PostgreSQL. Si el CSV no es válido, el procesamiento falla y no publica ningún mensaje al topic de salida.

El PR se fusiona automáticamente a `main` cuando pasan los checks y después se elimina la rama origen.


## Seguridad de rutas CSV

El fichero recibido desde Kafka solo se procesa si su ruta absoluta, resuelta con `toRealPath()`, permanece dentro de `CSV_ALLOWED_ROOT`. Se rechazan rutas relativas, ficheros inexistentes/no legibles y escapes mediante symlink.

```text
CSV_ALLOWED_ROOT=/data/csv
```


## Contratos Avro compartidos

`FileEventKey`, `FileEventValue`, `StatsMatchKey` y `StatsMatchValue` se consumen desde `com.fernandez.basketball:basketball-event-contracts:1.0.2`. Este repositorio ya no mantiene copias locales de esos schemas.
