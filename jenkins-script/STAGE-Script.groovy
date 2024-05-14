node{
    stage('checkout'){
      //  checkout([$class: 'GitSCM', branches: [[name: '*/MattelFPCorpHotfx_10June2020']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github_id', url: 'https://github.com/mattel-dig/AEM-Main.git']]])
        checkout([$class: 'GitSCM', branches: [[name: '*/shopify-dev-integration']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github_id', url: 'https://github.com/mattel-dig/AEM-Main.git']]])
    }
    stage('Build and Junit Test'){
        try{
        dir("${WORKSPACE}/mattel-web/ag") {
         sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
         dir("${WORKSPACE}/mattel-web/global") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
         dir("${WORKSPACE}/mattel-web/ecomm") {
        sh 'mvn clean install'
       //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/fisher-price") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
      dir("${WORKSPACE}/mattel-web/global-config") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/play") {
        sh 'mvn clean install'
      // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/corp") {
        sh 'mvn clean install'
      // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/informational") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }   
        dir("${WORKSPACE}/mattel-web/productvideos") {
		sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
		}finally{
        junit allowEmptyResults: true, keepLongStdio: true, testResults: '**/target/surefire-reports/*.xml'
        step([$class: 'JacocoPublisher', 
      execPattern: '**/target/*.exec',
      classPattern: '**/target/classes',
      sourcePattern: '**/src/main/java',
      exclusionPattern: '**/src/test*' ])
        }
    }
    
    stage('SonarQube Analysis') {
       // build job: 'SonarReport'
     } 
    stage('Deployment'){
      // build(job: 'DevDeployment', propagate: false)
       build(job: 'Stage Deployment', propagate: true)
    }
    stage('Nexus_Upload'){
        //build 'Upload to Nexus'
    }
    stage('Cache Clearance'){
        //build(job: 'Dev Cache Clearance', propagate: false)
        //build(job: 'QA Cache Clearance', propagate: false)
        build(job: 'Stage1_CacheClearance', propagate: false)
        build(job: 'Stage2_CacheClearance', propagate: false)
    } 
}