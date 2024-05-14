node {
    stage('checkout'){
       deleteDir()
       checkout([$class: 'GitSCM', branches: [[name: '*/master']], 
       doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], 
       userRemoteConfigs: [[credentialsId: 'github_id', url: 'https://github.com/mattel-dig/AEM-Main.git']]])
    
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
       // sh 'mvn clean install -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        
        dir("${WORKSPACE}/mattel-web/ag") {
        sh 'mvn clean install'
       //sh 'mvn clean install -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/fisher-price") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/play") {
         sh 'mvn clean install'
        //sh 'mvn clean install  -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        dir("${WORKSPACE}/mattel-web/global-config") {
        sh 'mvn clean install'
       // sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }
        /* dir("${WORKSPACE}/mattel-web/corp") {
        sh 'mvn clean install'
        //sh 'mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
        }*/
        dir("${WORKSPACE}/mattel-web/informational") {
       sh 'mvn clean install'
     //  sh 'mvn clean install -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -B -V'
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
    stage('SonarQube Analysis') {
   //     build job: 'Sonar_Dev'
     }
    stage('Deployment'){
        build(job: 'DevDeployment', propagate: false)
     //   build(job: 'Dec19Release_QA', propagate: false)
     //   build(job:'LogCopy', propodate:false)
     //   build(job: 'Dec19Release_Preprod', propagate: false)
    }
     parallel firstBranch: {
     //  build(job:'LogCopy', propagate:false)
    }, secondBranch: {
      //   build(job: 'DevDeployment', propagate: false)
    }//,
  //  failFast: false|false
    
    stage('Nexus_Upload'){
      //build 'Nexus Movement'
    }
    stage('Cache Clearance'){
        build(job: 'Dev Cache Clearance', propagate: false)
      //  build(job: 'QA  Cache Clearance', propagate: false)
      //  build(job: 'Preprod Cache Clearance', propagate: false)
       // build(job: 'Preprod 2 Cache Clearance', propagate: false)
       
    } 
}