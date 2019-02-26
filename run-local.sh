#!/usr/bin/env bash

mvn -Pdirect-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.bitcoinetl.PubSubToBigQueryPipeline \
-Dexec.args="--argsFile=config/pubsub-to-bigquery-pipeline/local.properties --defaultSdkHarnessLogLevel=DEBUG"

