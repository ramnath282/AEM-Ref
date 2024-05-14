import javax.jcr.Node
import com.day.cq.wcm.api.PageManager
import com.day.cq.wcm.msm.api.RolloutManager
import com.day.cq.wcm.msm.api.RolloutManager.RolloutParams

noOfNodesUpdated = 0;
List<String> resourceTypeList;
 
List<String> resourceTypeDMList ;

path = "/content/fisher-price/language-masters"
envAssetID = "mattelsites"
// envAssetID = "mattelsitesstage"
UpdateNodes()

def UpdateNodes(){
    /*Pathfield which needs to be iterated for an operation*/
 final def page = getPage(path)
 List<String> resourceTypeList =  new ArrayList<String>();
 resourceTypeList.add("mattel/ecomm/fisher-price/components/content/responsivegrid");
 resourceTypeList.add("mattel/ecomm/fisher-price/components/content/card");
def flag = 0;
 Iterator itr = resourceTypeList.iterator();
    while(itr.hasNext()){
        int count = 0;
        String resourceType = itr.next();
            def query  = buildQuery(page, resourceType);
            def result = query.execute();
            result.nodes.each { dmNode ->
                                String nodePath = dmNode.path;
                                println nodePath
                                if(dmNode.hasProperty("assetIDimage")){
                                    	count ++ ;
                					String assetIDimage = dmNode.getProperty("assetIDimage").getString();
                					println " Main Image : " +  assetIDimage
                					String updateAssetIDimage = envAssetID.concat(assetIDimage.substring(assetIDimage.indexOf('/'),assetIDimage.length()));
                					println("After change :" + updateAssetIDimage);
                					dmNode.setProperty("assetIDimage",updateAssetIDimage);
                					flag =1;
                				}
                				if(dmNode.hasProperty("imageserverurl")){
                				    dmNode.setProperty('imageserverurl','https://s7d1.scene7.com/is/image/');
                				    flag =1;
                				}
                				if(dmNode.hasProperty("videoserverurl")){
                				    dmNode.setProperty('videoserverurl','https://s7d1.scene7.com/is/content/');
                				    flag =1;
                				}
                				if(dmNode.hasProperty("viewerPath")){
                				      dmNode.setProperty('viewerPath','https://s7d1.scene7.com/s7viewers/');
                				      flag = 1;
                				}
                				if(dmNode.hasProperty("assetIDmobileImage")){
                					String assetIDMobileImage = dmNode.getProperty("assetIDmobileImage").getString();
                					println "Mobile Image : "+assetIDMobileImage
                					String updateAssetIDMobileImage = envAssetID.concat(assetIDMobileImage.substring(assetIDMobileImage.indexOf('/'),assetIDMobileImage.length()));
                					println("After Change : "+updateAssetIDMobileImage);
                					dmNode.setProperty("assetIDmobileImage",updateAssetIDMobileImage);
                					flag = 1;
                				}
                		       if(flag == 1){
                			   Page rolloutPage =  pageManager.getContainingPage(nodePath);
                			   rollout(rolloutPage)
                		       }
                		       flag =0;            
            }
            
        println 'count of  items :' + count    
    }     
}

def rollout(Page page){
    RolloutManager.RolloutParams params = new RolloutManager.RolloutParams();
    params.isDeep = false;
    params.reset = false;
    params.trigger = RolloutManager.Trigger.ROLLOUT;
    params.master = page;
    def rolloutManagerService = getService("com.day.cq.wcm.msm.api.RolloutManager")
       println 'Rolling out' + page.getPath() 
       rolloutManagerService.rollout(params);
       session.save();  
}

def buildQuery(page, term) {
def queryManager = session.workspace.queryManager;
def statement = 'select * from nt:base where jcr:path like \''+page.path+'/%\' and sling:resourceType = \'' + term + '\'';
queryManager.createQuery(statement, 'sql');
}