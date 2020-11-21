#!/usr/bin/env bash

mvn -e -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.ethereum_partitioned.EthereumPubSubToBigQueryPartitionedPipeline \
-Dexec.args="--chainConfigFile=chainConfigSample.json \
--allowedTimestampSkewSeconds=36000 \
--gcpTempLocation=gs://crypto-etl-ethereum-dev-dataflow-temp/dataflow \
--tempLocation=gs://crypto-etl-ethereum-dev-dataflow-temp/dataflow \
--project=crypto-etl-ethereum-dev \
--runner=DataflowRunner \
--jobName=ethereum-pubsub-to-bigquery-5 \
--workerMachineType=n1-standard-1 \
--maxNumWorkers=1 \
--diskSizeGb=30 \
--region=us-central1 \
--zone=us-central1-a \
"
