@Library('build-shared-library')_
def mode="withimage"

podTemplate(inheritFrom: "maven-jdk11", showRawYaml: false) {
    node(POD_LABEL) {
        container('maven') {
		
           stage('Switch-case configuration'){
            def mergeStatus= sh script: " echo '${gitlabMergeRequestState}' | tr -d '\\n'",returnStdout:true  
            def shouldSwitch = mergeStatus.contains('opened')
              if(shouldSwitch.toBoolean()){  
              mode="withoutimage"
              }      
           }
		   
           if(mode=="withimage")
           {gitlabCommitStatus(name:"00-PR accepted pipeline starting" , connection:gitLabConnection("${env.gitlabConnectionName}")){}}
           else
           {gitlabCommitStatus(name:"00-PR creation/update pipeline starting" , connection:gitLabConnection("${env.gitlabConnectionName}")){}}
		
            repositoryCheckout.setContext(this)
            repositoryCheckout.setCredential("${scm.userRemoteConfigs[0].credentialsId}")
            if(mode=="withimage")
            {repositoryCheckout.setBranch("${gitlabTargetBranch}")}
            else
            {repositoryCheckout.setBranch("${gitlabSourceBranch}")}
            repositoryCheckout.executeStep()
			
            environmentConfiguration.setContext(this)
            environmentConfiguration.executeStep()
			
            artifactoryTokenGenerator.setContext(this)
            artifactoryTokenGenerator.setArtifactoryCredential("${defaultArtifactoryAdminCredentialName}")
            artifactoryTokenGenerator.setArtifactoryGroupToken("CI")
            artifactoryTokenGenerator.executeStep()
			
            artifactoryConfiguration.setContext(this)
            artifactoryConfiguration.setEnvironmentConfiguration(this.environmentConfiguration)
            artifactoryConfiguration.setArtifactoryToken(artifactoryTokenGenerator.getToken())
            artifactoryConfiguration.executeStep()
			
            //sonarqubeAnalyzer.setContext(this)
            //if(mode=="withimage")
            //{sonarqubeAnalyzer.setBranch("${gitlabTargetBranch}")}
            //else
            //{sonarqubeAnalyzer.setBranch("${gitlabSourceBranch}")}
            //sonarqubeAnalyzer.setProjectKey("${gitlabTargetNamespace}_${gitlabTargetRepoName}")
            //sonarqubeAnalyzer.setProject("${gitlabMergeRequestTargetProjectId}")
            //sonarqubeAnalyzer.setMergeID("${gitlabMergeRequestIid}")
            //sonarqubeAnalyzer.enableSendMessage()
            //sonarqubeAnalyzer.executeStep()
			
            mavenProjectBuilder.setContext(this)
            mavenProjectBuilder.setDeployer(artifactoryConfiguration.getDeployer())
            mavenProjectBuilder.setBuildInfo(artifactoryConfiguration.getBuildInfo())
            mavenProjectBuilder.executeStep()
			
            if(mode=="withimage")
            {
            artifactPublisher.setContext(this)
            artifactPublisher.setServer(artifactoryConfiguration.getServer())
            artifactPublisher.setDeployer(artifactoryConfiguration.getDeployer())
            artifactPublisher.setBuildInfo(artifactoryConfiguration.getBuildInfo())
            artifactPublisher.executeStep()
			
						
            imageConfiguration.setContext(this)
            imageConfiguration.setTimezone("America/Asuncion")
            imageConfiguration.setHarborProject("apis")
            imageConfiguration.executeStep()
            }
        }
		
        if(mode=="withimage")
        {
        container('kaniko') {
            imageBuilder.setContext(this)
            imageBuilder.setHarborRobotAccountCredential("${defaultHarborRobotAccountCredentialName}")
            imageBuilder.setEnvironmentConfiguration(this.environmentConfiguration)
            imageBuilder.setImageConfiguration(this.imageConfiguration)
            imageBuilder.withPush()
            imageBuilder.executeStep()			
        }

        container('maven') {
            imageLabeler.setContext(this)
            imageLabeler.setHarborCredential("${defaultHarborAdminCredentialName}")
            imageLabeler.setEnvironmentConfiguration(this.environmentConfiguration)
            imageLabeler.setImageConfiguration(this.imageConfiguration)
            imageLabeler.executeStep()
			
            deploymentTrigger.setContext(this)
            deploymentTrigger.setProject("${gitlabMergeRequestTargetProjectId}")
            deploymentTrigger.setMergeID("${gitlabMergeRequestIid}")
            deploymentTrigger.setEnvironmentConfiguration(this.environmentConfiguration)
            deploymentTrigger.setImageConfiguration(this.imageConfiguration)
            deploymentTrigger.executeStep()
			
            gitlabCommitStatus(name:"11-Generated Image: ${imageConfiguration.getimageName()}" , connection:gitLabConnection("${env.gitlabConnectionName}")){}
            gitlabCommitStatus(name:"12-Generated Tag: ${imageConfiguration.getimageTag()}}" , connection:gitLabConnection("${env.gitlabConnectionName}")){}
                     									
        }
        }
    }
}