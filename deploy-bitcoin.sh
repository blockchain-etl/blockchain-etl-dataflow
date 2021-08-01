#!/usr/bin/env bash

mvn -e -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.bitcoin.BitcoinPubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=chainConfigBitcoinDev.json \
--allowedTimestampSkewSeconds=36000 \
--gcpTempLocation=gs://your-temp-bucket/dataflow \
--tempLocation=gs://your-temp-bucket/dataflow \
--project=your-project \
--runner=DataflowRunner \
--jobName=bitcoin-pubsub-to-bigquery-`date +"%Y%m%d-%H%M%S"` \
--workerMachineType=n1-standard-1 \
--maxNumWorkers=1 \
--diskSizeGb=30 \
--region=us-central1 \
--zone=us-central1-a \
"
