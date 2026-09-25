Current version: **2.0.3**

# csv-stats-match-parser

Parser separado de `csv-stats-match`.

```text
file.ready.match-summary -> CSV parser -> stats-match.parsed
```

Consume solo `MATCH_SUMMARY`, deriva `matchId` del nombre `MATCH_SUMMARY_<matchId>.csv`, exige exactamente una fila de 19 columnas y publica un único mensaje Avro con el resumen parseado.

No contiene JPA, Flyway ni PostgreSQL. Si el CSV no es válido, el procesamiento falla y no publica ningún mensaje al topic de salida.

El PR se fusiona automáticamente a `main` cuando pasan los checks y después se elimina la rama origen.
