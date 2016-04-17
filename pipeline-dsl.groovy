def gitUrl = params["BUILD_SCM_URL"]
def revision = params["BUILD_COMMIT"]
def branchName = params["BUILD_BRANCH_NAME"]
def simpleBranchName = params["BUILD_SIMPLE_BRANCH_NAME"]
def buildName = params["BUILD_NAME"]
def pipelineId = buildName+"-"+build.number

build("initialise-pipeline", BUILD_SCM_URL:gitUrl,
                            BUILD_COMMIT:revision,
                            BUILD_BRANCH_NAME:branchName,
                            BUILD_SIMPLE_BRANCH_NAME:simpleBranchName,
                            BUILD_NAME:buildName,
                            PIPELINE_ID:pipelineId )
build("build-maven-project", PIPELINE_ID:pipelineId )

parallel (
    { build("unit-test", PIPELINE_ID:pipelineId) },
    { build("integration-test", PIPELINE_ID:pipelineId) }
)

switch(simpleBranchName) {

    case ~/(^feature\/.*)/:
        build("merge-branch-to-target", PIPELINE_ID:pipelineId, TARGET_BRANCH:"develop")
        break

    case ~/(^develop$)/:
        // nothing to do
        break

    case ~/(^candidate\/.*)/:
        build("create-release", PIPELINE_ID:pipelineId)
        break

    case ~/(^release\/.*)/:
        build("merge-branch-to-target", PIPELINE_ID:pipelineId, TARGET_BRANCH:"master")
        build("merge-branch-to-target", PIPELINE_ID:pipelineId, TARGET_BRANCH:"develop")
        build("prepare-next-development-version", PIPELINE_ID:pipelineId)
        break
}
