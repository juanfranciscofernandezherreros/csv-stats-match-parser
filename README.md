Current version: **1.0.0**

# csv-stats-match-parser

Separa el parseo de `csv-stats-match`.

```text
file.ready.match-summary -> CSV parser -> stats-match.parsed
```

Consume solo `MATCH_SUMMARY`, deriva `matchId` del nombre `MATCH_SUMMARY_<matchId>.csv`, exige exactamente una fila de 19 columnas y publica Avro `PARSED` o `FAILED`.

No contiene JPA, Flyway ni PostgreSQL. El PR se fusiona automáticamente a `main` cuando pasan los checks y después se elimina la rama origen.
