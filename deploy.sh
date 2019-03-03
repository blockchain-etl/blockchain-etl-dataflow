#!/usr/bin/env bash

mvn -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.bitcoinetl.PubSubToBigQueryPipeline \
-Dexec.args="--argsFile=config/pubsub-to-bigquery-pipeline/dev.properties"
