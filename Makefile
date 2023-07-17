# Initialise `ENV` variable to `dev` if `ENV` is not a defined environment variable
ENV?=dev
# Extract current repo's URL and remove the `.git` suffix
repo_url:=$(shell git config --get remote.origin.url)
repo_url:=$(repo_url:.git=)
# Declare gcp variables
gcp_region:=us
gcp_ar_docker_region:=$(gcp_region)-docker.pkg.dev
image_name:=blockchain-etl-dataflow
gcp_ar_url:=$(gcp_ar_docker_region)/$(ENV)-im-data/dataflow

# Declare default image name to be built
tag_commit:=$(shell git log --format=short --pretty="format:%h" -1)

############################################################################################################################################################
# Make command to build Docker image                                                                                                                  ######
# - `--platform linux/amd64`. ==> for linux/amd65 platform                                                                                            ######
############################################################################################################################################################

docker.build:
	docker build --platform linux/amd64 -t $(image_name):$(tag_commit) -f Dockerfile --build-arg env=$(ENV) .

############################################################################################################################################################
# Make command to build docker image and publish image to AWS ECR                                                                                     ######
# - `ecr.push: docker.build`  ==> Invokes `docker.build` before executing steps below                                                                 ######
############################################################################################################################################################

define push_image
	docker tag $1:$(tag_commit) $(gcp_ar_url)/$1:$(tag_commit)
	docker push $(gcp_ar_url)/$1:$(tag_commit)
	docker tag $1:$(tag_commit) $(gcp_ar_url)/$1:latest
	docker push $(gcp_ar_url)/$1:latest
endef

gcp.ar.login:
	gcloud auth configure-docker $(gcp_ar_docker_region)

gcp.ar.push: docker.build gcp.ar.login
	$(call push_image,${image_name})

generate.jar:
	mvn -e -Pdataflow-runner package -DskipTests