import javax.jcr.Node
import javax.jcr.Node
import org.apache.jackrabbit.vault.packaging.*
import org.apache.jackrabbit.vault.fs.api.*
import org.apache.jackrabbit.vault.fs.config.*
import org.apache.jackrabbit.vault.util.*
import org.apache.jackrabbit.vault.fs.filter.*
import org.apache.sling.api.resource.ResourceResolverFactory.*
import org.apache.jackrabbit.commons.JcrUtils;
import javax.jcr.Session.*
import java.util.*


// Brand Category Liting v1 -- BrandCategoryListingv1

def testRun = false
/*Flag to count the number of pages Updated */
noOfPagesUpdated = 0

/*Flag to count the number of pages Added to the Package */
noOfPagesUpdatesForDelete = 0

List<String> pageListDetails;
path='/content/fisher-price/language-masters'
propName='sling:resourceType'
legacyComponentValue='mattel/ecomm/fisher-price/components/content/brandnCategoryListing'
isParentList=false;
Node newNode;

modifyPropery()

def modifyPropery(){    
    pageListDetails = new ArrayList<String>();
    getPage(path).recurse
    {
        page ->
        def jcrContentNode = page.node;
        String currentPagePath=page.getPath();
        NodeIterator itr=jcrContentNode.getNodes();
        iterateNode(itr, currentPagePath);
    }
}

def iterateNode(NodeIterator itr,  currentPagePath){
    isParentList=false;
    
    while(itr.hasNext()){
        
    Node childNode=itr.nextNode();
    
     if(childNode.hasProperty('sling:resourceType') && childNode.getProperty('sling:resourceType').getString().equals('mattel/ecomm/fisher-price/components/content/brandnCategoryListing')){
               pageListDetails.add(currentPagePath.toString());               
               println 'modified page -> ' + currentPagePath.toString();               
               Node parentNode=childNode.getParent();            
               String nodeName = childNode.getName().toString();
               String newNodeName='list';
               if(nodeName.contains('_')){
                   
                   newNodeName=newNodeName + '_'+nodeName.substring(nodeName.indexOf('_')+1,nodeName.length()).toString();
               }
              
              try{
              //   newNode = parentNode.getNode(newNodeName);  
               newNode = parentNode.addNode(newNodeName);
               newNode.setProperty('jcr:title','list');
               newNode.setProperty('sling:resourceType','mattel/ecomm/fisher-price/components/content/list'); 
               parentNode.orderBefore(newNode.getName().toString(),childNode.getName().toString());
               noOfPagesUpdated =noOfPagesUpdated+1;  
               if(!newNode.get("showMoreText") && childNode.get("showMoreText") != null) {
                  String showMoreText = childNode.get("showMoreText").toString();
                  newNode.setProperty("showMoreText", showMoreText);
               }
               
               if(!newNode.get("trackingText") &&  childNode.get("analyticsText") != null) {
                   newNode.setProperty("trackingText", childNode.get("analyticsText").toString());
               }
               
                if(childNode.get("sectionTitle") != null) {
                   newNode.setProperty("title", "<h2>"+ childNode.get("sectionTitle").toString() + "</h2>");
               }
               
               newNode.setProperty("sortOrder","asc");
               if(childNode.get("listingFor") != null && childNode.get("listingFor") == "category"){
                    newNode.setProperty("xlColumns","6");
                    newNode.setProperty("largeColumns","6");
                    newNode.setProperty("mediumColumns","4");
                    newNode.setProperty("smallColumns","2");
                    newNode.setProperty("xsColumns","2");
					// Full Bleed , Text Center
                    String[] values = ['1579331902884','1579331977985'];
                    newNode.setProperty("cq:styleIds",values)
               }
                if(childNode.get("listingFor") != null && childNode.get("listingFor") == "brand"){
                    newNode.setProperty("xlColumns","5");
                    newNode.setProperty("largeColumns","5");
                    newNode.setProperty("mediumColumns","4");
                    newNode.setProperty("smallColumns","2");
                    newNode.setProperty("xsColumns","2");
					// Full Bleed , Text Center , Grid List OFF
                    String[] values = ['1579331902884','1579331977985','1580460244036'];
                    newNode.setProperty("cq:styleIds",values)
               }
               if(childNode.get("showLessText") != null && childNode.get("showMoreText") != null){
                   newNode.setProperty("showMoreFeature","true");
                   newNode.setProperty("showMoreRepeat","true");
                   newNode.setProperty("xlInitial","12");
                   newNode.setProperty("largeInitial","12");
                   newNode.setProperty("mediumInitial","1");
                   newNode.setProperty("smallInitial","2");
                   newNode.setProperty("xsInitial","2");
                   newNode.setProperty("xlShowMore","12");
                   newNode.setProperty("largeShowMore","12");
                   newNode.setProperty("mediumShowMore","12");
                   newNode.setProperty("smallShowMore","12");
                   newNode.setProperty("xsShowMore","12");
                   
                }
               if(!newNode.get("showLessText")  &&  childNode.get("showLessText") != null) {
                   newNode.setProperty("showLessText", childNode.get("showLessText").toString());
               }
               
              if(!newNode.get("listFrom") &&  childNode.get("listFrom").toString()== "static") {
                    newNode.setProperty("listFrom", "static");
                    Property nProp = childNode.getProperty("pageList");
                    newNode.setProperty("contentPages",nProp.getValues());
                   
                } 
                
                if(!newNode.get("listFrom") &&  childNode.get("listFrom").toString()== "children") {
                   newNode.setProperty("listFrom", "children");
                   newNode.setProperty("parentPage",currentPagePath );
                }
               
                
              } catch (ItemExistsException ex){
                  
                  println(ex)
                  
              }
             
        }
        
        if(childNode.hasNodes()){
                     NodeIterator inrItr=childNode.getNodes(); 
                     iterateNode(inrItr ,currentPagePath);
         }
                     
    }
    
}

if(!testRun){
   println ("node created, proceeding to save");   
   modifiedPageList();	
}

def modifiedPageList(){
   println ("Adding page modified details at : /var/groovyconsole");     
   Node groovyNode = getNode('/var/groovyconsole');
   Node modifiedPageDetails = JcrUtils.getOrAddNode(groovyNode,'modifiedPageDetails','nt:unstructured');
   String [] pageListDetailsArray = pageListDetails.toArray(new String[0]);
   modifiedPageDetails.setProperty("BrandnCategoryList",pageListDetailsArray);
   save();

}

println ("Is it Test Run : "  + testRun);
println ("Total Number of  Pages Updated  :" + noOfPagesUpdated);