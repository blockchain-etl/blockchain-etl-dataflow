FROM gcr.io/dataflow-templates-base/java8-template-launcher-base:latest

ARG env

ENV FLEX_TEMPLATE_JAVA_CLASSPATH=/template/*
ENV FLEX_TEMPLATE_JAVA_MAIN_CLASS=io.blockchainetl.ethereum.EthereumPubSubToBigQueryPipeline

COPY target/blockchain-etl-dataflow-bundled-0.1.jar /template/
COPY chain-config/blockchain_zkevm_imtbl_testnet_13392_${env}.json /template/