locals {
  source_code = "im-data-blockchain-etl-dataflow"
  region      = "us-central1"
  zone      = "us-central1-a"
  team_owner  = "data"
  tags = {
    team_owner  = local.team_owner
    terraform   = "true"
    environment = "${terraform.workspace}"
    source      = "${local.source_code}"
  }
}