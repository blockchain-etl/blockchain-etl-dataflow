#!/usr/bin/env bash

mvn -Pdirect-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.eos.EosPubSubToBigQueryPipeline \
-Dexec.args="--chainConfigFile=chainConfigEos.json --allowedTimestampSkewSeconds=360000 --defaultSdkHarnessLogLevel=DEBUG \
--tempLocation=gs://your-bucket/dataflow"

