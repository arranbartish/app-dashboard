def gitUrl = params["BUILD_SCM_URL"]
def revision = params["BUILD_COMMIT"]
def branchName = params["BUILD_BRANCH_NAME"]
def simpleBranchName = params["BUILD_SIMPLE_BRANCH_NAME"]
def buildName = params["BUILD_NAME"]
def pipelineId = buildName+"-"+build.number

build("initialise-pipeline", BUILD_SCM_URL:gitUrl,BUILD_COMMIT:revision,BUILD_BRANCH_NAME:branchName,BUILD_SIMPLE_BRANCH_NAME:simpleBranchName, BUILD_NAME:buildName, PIPELINE_ID:pipelineId )
build("build-maven-project", PIPELINE_ID:pipelineId)
