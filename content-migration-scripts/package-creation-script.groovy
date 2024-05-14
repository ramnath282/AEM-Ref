import javax.jcr.Node
import javax.jcr.Property
import javax.jcr.Value
import javax.jcr.PropertyIterator
import org.apache.jackrabbit.vault.packaging.*
import org.apache.jackrabbit.vault.fs.api.*
import org.apache.jackrabbit.vault.fs.config.*
import org.apache.jackrabbit.vault.util.*
import org.apache.jackrabbit.vault.fs.filter.*
import org.apache.sling.api.resource.ResourceResolverFactory.*
import org.apache.jackrabbit.commons.JcrUtils;
import com.day.cq.wcm.msm.api.RolloutManager
import com.day.cq.wcm.msm.api.RolloutManager.RolloutParams
import javax.jcr.Session.*
import java.util.*


// Package Creation

/*Flag to count the number of pages Added to the Package */
noOfFiltersAdded = 0

/*Pathfield which needs to be iterated for an operation*/
HashSet<PathFilterSet> pathFilterSets;
HashSet<String> rolloutPageList;
getModifiedPageList();

def getModifiedPageList(){
    String s;
    rolloutPageList = new HashSet<String>();
    pathFilterSets = new HashSet<PathFilterSet>();
    Node node = getNode("/var/groovyconsole/modifiedPageDetails");
    println node.getPath();
    int count = 0;
   PropertyIterator itr =node.getProperties("*List");
   while(itr.hasNext()){
       Property property = itr.next();
       for(Value v in property.getValues()){
           count++;
           s= v.getString();
           rolloutPageList.add(s);
           PathFilterSet principal = new PathFilterSet(s);
           pathFilterSets.add(principal);
           println s;
       }
   }
    println 'modified page count' + count
    println 'Unique Page count' + pathFilterSets.size();   
     println 'Roll Unique Page count' + rolloutPageList.size();   
    deleteNodes();
    rollout();
  //  createPackage();
}

def rollout(){
    RolloutManager.RolloutParams params = new RolloutManager.RolloutParams();
    params.isDeep = false;
     params.reset = false;
     params.trigger = RolloutManager.Trigger.ROLLOUT;
    Iterator itr=rolloutPageList.iterator();
    int i = 0;
    while(itr.hasNext()){
        String masterPath = itr.next();
        Page page = getPage(masterPath);
        params.master = page;
    
       def rolloutManagerService = getService("com.day.cq.wcm.msm.api.RolloutManager")
       println 'Rolling out' + page.getPath() 
       rolloutManagerService.rollout(params);
       save();
       i++;
    }
    println 'Rolled out page count' + i;
}

def deleteNodes(){
    /*Pathfield which needs to be iterated for an operation*/
 path ='/content/fisher-price/language-masters'
 final def page = getPage(path)
 List<String> resourceTypeList =  new ArrayList<String>();
 resourceTypeList.add("mattel/play/components/content/promoCarousel");
 resourceTypeList.add("mattel/ecomm/fisher-price/components/content/brandnCategoryListing");
 resourceTypeList.add("mattel/play/components/content/downloadImageGallery");
 resourceTypeList.add("mattel/play/components/content/imageAndText");
 Iterator itr = resourceTypeList.iterator();
    while(itr.hasNext()){
        int count = 0;
        String resourceType = itr.next();
            def query  = buildQuery(page, resourceType);
            def result = query.execute();
            result.nodes.each { node ->
                                String nodePath = node.path;
                                if(node.getProperty('sling:resourceType').getString().equals(resourceType) && !nodePath.contains('jcr:versionStorage') ){
									boolean toBeDeleted = true;
									if(node.getProperty('sling:resourceType').getString().equals("mattel/play/components/content/imageAndText") && node.getProperty('imageTextWidthRatio').getString().equals('media-50-50')){
										toBeDeleted = false;
									}
									if(toBeDeleted){
										count ++;
										println 'deleting--'+nodePath ;
										node.remove();
									}
                                }
            /* Save this session if you are sure the correct nodes are being deleted. Once the session is saved the nodes couldn't be retrieved back. */
                session.save();
            }
            
        println 'count of deleted items :' + count    
    }     
}

//deleting the legacy nodes

def buildQuery(page, term) {
def queryManager = session.workspace.queryManager;
def statement = 'select * from nt:base where jcr:path like \''+page.path+'/%\' and sling:resourceType = \'' + term + '\'';
queryManager.createQuery(statement, 'sql');
}

//packaging the output 
def createPackage(){
    JcrPackageManager pkgMgr=PackagingService.getPackageManager(session)
    JcrPackage jcrPack=pkgMgr.create('ComponentReusability','migratedPagePackage'+Math.abs(new Random().nextInt() % 600 + 1));
    JcrPackageDefinition jcrPackageDefinition = jcrPack.getDefinition();
    DefaultWorkspaceFilter workspaceFilter = new DefaultWorkspaceFilter();
    Iterator itr=pathFilterSets.iterator();
    while(itr.hasNext()){
    workspaceFilter.add(itr.next());
    noOfFiltersAdded=noOfFiltersAdded+1;
    }
    jcrPackageDefinition.setFilter(workspaceFilter,true);
    ProgressTrackerListener listener = new DefaultProgressListener();
    pkgMgr.assemble(jcrPack,listener);
}