#!/usr/bin/env bash

mvn -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.bitcoinetl.PubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=chainConfigDev.json \
--allowedTimestampSkewSeconds=36000 \
--gcpTempLocation=gs://your-temp-bucket/dataflow \
--project=your-project \
--runner=DataflowRunner \
--jobName=crypto-etl-pubsub-to-bigquery \
--workerMachineType=n1-standard-1 \
--maxNumWorkers=1 \
--diskSizeGb=30 \
--region=us-central1 \
--zone=us-central1-a \
"
