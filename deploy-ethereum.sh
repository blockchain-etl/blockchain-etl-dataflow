#!/usr/bin/env bash

mvn -e -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.ethereum.EthereumPubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=chainConfigEthereumDev.json \
--allowedTimestampSkewSeconds=36000 \
--gcpTempLocation=gs://your-temp-bucket/dataflow \
--tempLocation=gs://your-temp-bucket/dataflow \
--project=your-project \
--runner=DataflowRunner \
--jobName=ethereum-pubsub-to-bigquery \
--workerMachineType=n1-standard-1 \
--maxNumWorkers=1 \
--diskSizeGb=30 \
--region=us-central1 \
--zone=us-central1-a \
"
