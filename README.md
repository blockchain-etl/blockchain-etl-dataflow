# blockchain-etl-dataflow

Dataflow pipelines for Bitcoin ETL. Connects Pub/Sub topics with BigQuery tables.

## Local development (All commands in Makefile)

- Generate the jar file `make generate.jar`
- Push the image to Google Artifact Repository `make gcp.ar.push`
- Deploy dataflow job in dev workspace using `terraform plan`

