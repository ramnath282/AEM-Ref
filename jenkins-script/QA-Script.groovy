node{
    try{
    //notifyStarted()
    stage('checkout'){
        deleteDir()
       // checkout([$class: 'GitSCM', branches: [[name: '*/ComponentReuse_POD']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github_id', url: 'https://github.com/mattel-dig/AEM-Main.git']]])
        checkout([$class: 'GitSCM', branches: [[name: '  */ADPB-2021Q3-R16']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github_id', url: 'https://github.com/mattel-dig/AEM-Main.git']]])
        sh 'git log -5'
    }
    stage('Build and Junit Test'){
       try{
        dir("${WORKSPACE}/mattel-web/global") {
        sh 'mvn clean install'
         //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/ecomm") {
        sh 'mvn clean install'
       // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/ag") {
        sh 'mvn clean install'
        // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/fisher-price") {
           sh 'mvn clean install' 
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
    dir("${WORKSPACE}/mattel-web/play") {
           sh 'mvn clean install'
       //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
       }
        dir("${WORKSPACE}/mattel-web/global-config") {
        sh 'mvn clean install'
      // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
     dir("${WORKSPACE}/mattel-web/informational") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/productvideos") {
         sh 'mvn clean install'
         //sh 'mvn clean install -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
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
   //stage('SonarQube Analysis') {
     //   build job: 'POD_Sonar'
 //}
    stage('Deployment'){
        build(job: 'POD_Deployment', propagate: false)
    }
    stage('Nexus'){
       // build(job: 'POD_Nexus', propagate: false)
    } 
     stage('Cache Clearance'){
        build(job: 'POD_CacheClearance', propagate: false)
     }
    }finally{
        //notifyJobComplete()
    }
}
def notifyStarted() {
    echo 'inside job start notification'
   emailext body: """STARTED: Job \'${env.JOB_NAME}\':
         Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]\\console</a>"\n""", 
		 subject: "STARTED: Job \'${env.JOB_NAME}\'", to: 'rohit.karmarkar@mattel.com'
 }
 def notifyJobComplete() {
    echo 'inside job complete method'
   emailext attachLog: true, body: """Success: Job \'${env.JOB_NAME}\':
         Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]\\console</a>"\n""", 
		 compressLog: true, subject: "Success: Job \'${env.JOB_NAME}\'", to: 'rohit.karmarkar@mattel.com'
 }