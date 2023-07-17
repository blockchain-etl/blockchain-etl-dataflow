resource "google_storage_bucket_object" "blockchain_etl_dataflow" {
  bucket       = "${terraform.workspace}-im-data-imx-resource"
  name         = "dataflow-templates/blockchain-etl-dataflow/metadata.json"
  content_type = "application/json"

  content = jsonencode({
    "defaultEnvironment": {},
    "image": "us-docker.pkg.dev/${terraform.workspace}-im-data/dataflow/blockchain-etl-dataflow:latest",
    "metadata": {
        "name": "Pipeline for streaming ethereum etl data into bigquery",
        "parameters": [
            {
                "helpText": "The path or location of the chain configuration file",
                "label": "Chain Configuration File",
                "name": "chainConfigFile"
            },
            {
                "helpText": "The maximum allowed time difference (in seconds) between the blockchain events and current time",
                "label": " Allowed Timestamp Skew (Seconds)",
                "name": "allowedTimestampSkewSeconds"
            },
            {
                "helpText": "The temporary storage location in Google Cloud Platform (GCP) used during data processing",
                "label": "GCP Temp Location",
                "name": "gcpTempLocation"
            },
            {
                "helpText": "The temporary storage location used during data processing",
                "label": "Temp Location",
                "name": "tempLocation"
            },
            {
                "helpText": "The identifier or name of the project in which the data processing job will run",
                "label": "GCP project identifier",
                "name": "project"
            },
            {
                "helpText": "The execution framework or technology used for running the data processing - Normally DataflowRunner",
                "label": "Runner",
                "name": "runner"
            },
            {
                "helpText": "Machine Type used in Dataflow, Ex: n1-standard-1, n1-standard-2",
                "label": "Worker Machine Type",
                "name": "workerMachineType"
            },
            {
                "helpText": "The maximum number of worker instances allowed for parallel data processing.",
                "label": "Maximum Number of Workers",
                "name": "maxNumWorkers"
            },
            {
                "helpText": "The size of the disk allocated for storing data during processing.",
                "label": "Disk Size (GB)",
                "name": "diskSizeGb"
            },
            {
                "helpText": "GCP Region",
                "label": "GCP Region",
                "name": "region"
            },
            {
                "helpText": "Zone inside the GCP Region",
                "label": "Zone inside the GCP Region",
                "name": "zone"
            }
        ]
    },
    "sdkInfo": {
        "language": "JAVA"
    }
  })
}


resource "google_dataflow_flex_template_job" "flex_template_job" {
  provider                     = google-beta
  project                      = "${terraform.workspace}-im-data"
  name                         = "zkevm-imtbl-testnet-13392-etl-dataflow-${formatdate("YYYYMMDD-hhmmss", timestamp())}"
  region                       = local.region
  skip_wait_on_job_termination = true
  container_spec_gcs_path      = "gs://${google_storage_bucket_object.blockchain_etl_dataflow.bucket}/${google_storage_bucket_object.blockchain_etl_dataflow.name}"
  on_delete                    = "drain"


  parameters = {
    chainConfigFile = "/template/blockchain_zkevm_imtbl_testnet_13392_${terraform.workspace}.json"
    allowedTimestampSkewSeconds = "1814400"
    gcpTempLocation = "gs://${terraform.workspace}-im-data-imx-resource/ethereum-etl/temp"
    tempLocation = "gs://${terraform.workspace}-im-data-imx-resource/ethereum-etl/temp"
    project = "${terraform.workspace}-im-data"
    runner = "DataflowRunner"
    workerMachineType = "n1-standard-1"
    maxNumWorkers = 1
    diskSizeGb=30
    enableStreamingEngine=true
    region=local.region
    zone=local.zone
  }
}