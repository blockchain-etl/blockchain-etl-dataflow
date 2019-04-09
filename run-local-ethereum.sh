#!/usr/bin/env bash

mvn -Pdirect-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.ethereum.EthereumPubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=chainConfigEthereumDev.json --allowedTimestampSkewSeconds=36000 --defaultSdkHarnessLogLevel=DEBUG \
--tempLocation=gs://your-bucket/dataflow"

