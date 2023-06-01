#!/usr/bin/env bash

mvn -e -Pdataflow-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.ethereum.EthereumPubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=immutable_zkevm_13372_config.json \
--allowedTimestampSkewSeconds=604800 \
--gcpTempLocation=gs://dev-im-data-imx-resource/ethereum-etl/temp \
--tempLocation=gs://dev-im-data-imx-resource/ethereum-etl/temp \
--project=dev-im-data \
--runner=DataflowRunner \
--jobName=ethereum-pubsub-to-bigquery-`date +"%Y%m%d-%H%M%S"` \
--workerMachineType=n1-standard-1 \
--maxNumWorkers=1 \
--diskSizeGb=30 \
--region=us-central1 \
--zone=us-central1-a \
"
