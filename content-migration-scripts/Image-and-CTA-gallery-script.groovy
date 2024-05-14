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

def testRun = true
/*Flag to count the number of pages Updated */
noOfPagesUpdated = 0

/*Pathfield which needs to be iterated for an operation*/
path='/content/fisher-price/language-masters'

resourceTypePropName='sling:resourceType'
legacyComponentResourceType='mattel/play/components/content/downloadImageGallery'
gridContainerResourceType='mattel/ecomm/fisher-price/components/content/gridContainer'
cardResourceType='mattel/ecomm/fisher-price/components/content/card'
ctaResourceType='mattel/ecomm/fisher-price/components/content/ctaItem'

environment_AssetID = 'mattelsitesqa';
List<String> pageListDetails;
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
		if(childNode.hasProperty(resourceTypePropName) && childNode.getProperty(resourceTypePropName).getString().equals(legacyComponentResourceType)){
			println "Page Path : "+childNode.path;
			pageListDetails.add(currentPagePath.toString());
    		Node parentNode=childNode.getParent();
    		
    		String nodeName = childNode.getName().toString();
    		String newNodeName='gridcontainer';
            if(nodeName.contains('_')){
               newNodeName=newNodeName + '_'+nodeName.substring(nodeName.indexOf('_')+1,nodeName.length()).toString();
            }
            
            try{
                //Node gridContainerNode = parentNode.addNode(newNodeName);
				Node gridContainerNode = checkIfNodeExists(parentNode, newNodeName);
                gridContainerNode.setProperty('sling:resourceType',gridContainerResourceType);
                def styles = ["1579333673030"] as String[];
				gridContainerNode.setProperty('cq:styleIds',styles);
                parentNode.orderBefore(gridContainerNode.getName().toString(),childNode.getName().toString());
                noOfPagesUpdated =noOfPagesUpdated+1;
				
                if(!gridContainerNode.get("columnLayout")) {
                      String columnLayout = childNode.get("columnLayout").toString();
                      String columnCount = columnLayout.substring(columnLayout.indexOf('-')+1,columnLayout.length()).toString()
                      
					  setDefaultNodePropertyValue(gridContainerNode,"xlColumns", columnCount);
					  setDefaultNodePropertyValue(gridContainerNode,"largeColumns", columnCount);
					  setDefaultNodePropertyValue(gridContainerNode,"mediumColumns", "2");
					  setDefaultNodePropertyValue(gridContainerNode,"smallColumns", "1");
					  setDefaultNodePropertyValue(gridContainerNode,"xsColumns", "1");
                }
				flag = false;
				if(childNode.get("showMoreText")!=null){
				    String showMoreText = childNode.get("showMoreText").toString();
				    flag = true;
				    gridContainerNode.setProperty('showMoreText',"<p>" + showMoreText + "</p>");
				}
				
				if(childNode.get("showLessText")!=null){
				    String showLessText = childNode.get("showLessText").toString();
				    flag = true;
				    gridContainerNode.setProperty('showLessText',"<p>" + showLessText + "</p>");
				}
				String enableviewall = childNode.get("enableviewall").toString();	
                if(flag || enableviewall == "true") {
                      //println "flag : " + flag;
					    
					  setDefaultNodePropertyValue(gridContainerNode,"viewAllCtaSwitch", enableviewall);
					  setPropertyValueToNode(childNode, gridContainerNode, "viewAll", "linkText");
					  setPropertyValueToNode(childNode, gridContainerNode, "viewAllLink", "linkURL");
					  setDefaultNodePropertyValue(gridContainerNode,"showMoreRepeat", "true");
					  setDefaultNodePropertyValue(gridContainerNode,"showMoreFeature", "true");	
					  
					  setDefaultNodePropertyValue(gridContainerNode,"xlInitial", "4");
					  setDefaultNodePropertyValue(gridContainerNode,"mediumInitial", "4");
					  setDefaultNodePropertyValue(gridContainerNode,"largeInitial", "4");
					  setDefaultNodePropertyValue(gridContainerNode,"smallInitial", "6");
					  setDefaultNodePropertyValue(gridContainerNode,"xsInitial", "6");
					  
					  
					  setDefaultNodePropertyValue(gridContainerNode,"xlShowMore", "2");
					  setDefaultNodePropertyValue(gridContainerNode,"largeShowMore", "2");
					  setDefaultNodePropertyValue(gridContainerNode,"mediumShowMore", "2");
					  setDefaultNodePropertyValue(gridContainerNode,"smallShowMore", "4");
					  setDefaultNodePropertyValue(gridContainerNode,"xsShowMore", "4");
					  
					  setDefaultNodePropertyValue(gridContainerNode,"ctaRepeat", "3");	
				}
				
				setPropertyValueToNode(childNode,gridContainerNode,"downloadBgColor","backgroundColor");
				setPropertyValueToNode(childNode,gridContainerNode,"downloadBackOption","backgroundOption");
				setPropertyValueToNode(childNode,gridContainerNode,"description","description");
               
				setDefaultNodePropertyValue(gridContainerNode,"scrollMobile", "false");
				setDefaultNodePropertyValue(gridContainerNode,"customMobile", "false");
				setDefaultNodePropertyValue(gridContainerNode,"tileImage", "false");
				
				if(childNode.get("title")!=null){
				    String mainTitle = childNode.get("title").toString();
				    gridContainerNode.setProperty('title',"<h2>" + mainTitle + "</h2>");
				}
				
				if(childNode.get("downloadBgImage") != null) {
					setPropertyValueToNode(childNode,gridContainerNode,"downloadBgImage","image");
					String imagePath = childNode.get("downloadBgImage").toString();
					String image = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.lastIndexOf("."));
					gridContainerNode.setProperty('assetIDimage',environment_AssetID+image);
			
					setDefaultNodePropertyValue(gridContainerNode,"assetType", "image");
					setDefaultNodePropertyValue(gridContainerNode,"breakpoints","");
					setDefaultNodePropertyValue(gridContainerNode,"imageserverurl","https://s7d1.scene7.com/is/image/");
					setDefaultNodePropertyValue(gridContainerNode,"s7ImagePreset","");
					setDefaultNodePropertyValue(gridContainerNode,"s7PresetType","smartCrop");
					setDefaultNodePropertyValue(gridContainerNode,"s7ViewerPreset","");
					setDefaultNodePropertyValue(gridContainerNode,"s7ViewerPresetPath","");
					setDefaultNodePropertyValue(gridContainerNode,"videoserverurl","https://s7d1.scene7.com/is/content/");
					setDefaultNodePropertyValue(gridContainerNode,"viewerPath","https://s7d1.scene7.com/s7viewers/");
					setDefaultNodePropertyValue(gridContainerNode,"viewerPresetPath","");
				}
				
                def str = ["true","true","true","true","true","true"] as String[];
                gridContainerNode.setProperty("textIsRich", str);
              
                setCardNode(gridContainerNode, childNode);
                
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


def setCardNode(Node gridContainerNode, Node childNode){
    //println "downaload image node is null " + childNode.hasNodes();
    if(childNode.hasNodes()){
        Node tileParentNode= childNode.getNode("downloadImage");
        NodeIterator tileNodeItr=tileParentNode.getNodes();
    
        while(tileNodeItr.hasNext()){ 
         Node tileNode = tileNodeItr.next();
         String tileNodeName = tileNode.getName().toString();
         String cardNodeName = "content_" + tileNodeName.substring(tileNodeName.indexOf('item')+4,tileNodeName.length()).toString();
         
         try{
              //Node cardNode = gridContainerNode.addNode(cardNodeName);
			  Node cardNode = checkIfNodeExists(gridContainerNode, cardNodeName);
              cardNode.setProperty(resourceTypePropName,cardResourceType);
             
			 if(tileNode.get("thumbnailTitle")!=null){
				String mainTitle = tileNode.get("thumbnailTitle").toString();
				cardNode.setProperty('title',"<h2>" + mainTitle + "</h2>");
			 }
				
			 setPropertyValueToNode(tileNode,cardNode,"thumbnailDescription","description");
			 setPropertyValueToNode(tileNode,cardNode,"altTexTthumbnail","imageAltText");
			 
			 
			 if(tileNode.get("thumbnailImage") != null) {
					setPropertyValueToNode(tileNode,cardNode,"thumbnailImage","image");
					String imagePath = tileNode.get("thumbnailImage").toString();
					String image = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.lastIndexOf("."));
					cardNode.setProperty('assetIDimage',environment_AssetID+image);
			
					setDefaultNodePropertyValue(cardNode,"assetType", "image");
					setDefaultNodePropertyValue(cardNode,"breakpoints","");
					setDefaultNodePropertyValue(cardNode,"imageserverurl","https://s7d1.scene7.com/is/image/");
					setDefaultNodePropertyValue(cardNode,"s7ImagePreset","");
					setDefaultNodePropertyValue(cardNode,"s7PresetType","smartCrop");
					setDefaultNodePropertyValue(cardNode,"s7ViewerPreset","");
					setDefaultNodePropertyValue(cardNode,"s7ViewerPresetPath","");
					setDefaultNodePropertyValue(cardNode,"videoserverurl","https://s7d1.scene7.com/is/content/");
					setDefaultNodePropertyValue(cardNode,"viewerPath","https://s7d1.scene7.com/s7viewers/");
					setDefaultNodePropertyValue(cardNode,"viewerPresetPath","");
				}
		
			  setDefaultNodePropertyValue(cardNode,"isCustomMobileEnabled", "false");
			  setDefaultNodePropertyValue(cardNode,"isHoverImageEnabled", "false");
			  setDefaultNodePropertyValue(cardNode,"entrCompClickable", "false");
              def str = ["true","true","true","true","true","true"] as String[];
              cardNode.setProperty("textIsRich", str);
			  def cardStyles = ["1578487372824"] as String[];
			  cardNode.setProperty('cq:styleIds',cardStyles);
              setCtaNode(cardNode,tileNode);
         }catch(ItemExistsException ex){
             println(ex)
         }
        }
    }
}

def setCtaNode(Node cardNode, Node tileNode){
	 try{
			//Node ctaNode = cardNode.addNode("cta_0");
			Node ctaNode = checkIfNodeExists(cardNode, "cta_0");
			ctaNode.setProperty(resourceTypePropName,ctaResourceType);

			setPropertyValueToNode(tileNode,ctaNode,"downloadCtaLabel","linkText");
			setPropertyValueToNode(tileNode,ctaNode,"downloadCtaLink","linkURL");
			
			if(!ctaNode.get("enableDownloadFile") || !ctaNode.get("openCtaLinksIn")) {
			  String enableDownloadFile = tileNode.get("enableDownloadFile").toString();
			  String openCtaLinksIn = tileNode.get("openCtaLinksIn").toString();
			  
			  if(enableDownloadFile== "true"){
				  ctaNode.setProperty("linkOptions", "download");
			  }else{
				  if(openCtaLinksIn == "newtab"){
					  ctaNode.setProperty("linkOptions", "newTab");
				  }else if(openCtaLinksIn == "newwindow"){
					  ctaNode.setProperty("linkOptions", "blank");
				  }else if(openCtaLinksIn == "samewindow"){
					  ctaNode.setProperty("linkOptions", "self");
				  }
			  }
			}
			
			if(!ctaNode.get("alwaysEnglish") && tileNode.get("alwaysEnglish") != null) {
			  String alwaysEnglish = tileNode.get("alwaysEnglish").toString();
			  ctaNode.setProperty("trackThisCTA", "true");
			  ctaNode.setProperty("trackingText", alwaysEnglish);
			}

			setDefaultNodePropertyValue(ctaNode,"useInterstitial", "false");
			setDefaultNodePropertyValue(ctaNode,"textIsRich", "true");
			
			def ctaStyle = ["1578995059742"] as String[];
			ctaNode.setProperty('cq:styleIds',ctaStyle);
			//println "\n";
	 }catch(ItemExistsException ex){
		  println(ex)
	 }   
}

def Node checkIfNodeExists(Node parentNode, String newNodeName){
    Node newNode;
    if(parentNode.hasNode(newNodeName)){
	    newNode = parentNode.getNode(newNodeName);
    }else{
        newNode = parentNode.addNode(newNodeName);
    }
    return newNode;
}

def setPropertyValueToNode(Node originalNode, Node nwNode, String oldAttrName, String newAttrName){
	if(!nwNode.get(oldAttrName)) {
		if(originalNode.get(oldAttrName) != null){
		  String oldAttrValue = originalNode.get(oldAttrName).toString();
		  nwNode.setProperty(newAttrName, oldAttrValue);
		  //println newAttrName + " : " + oldAttrValue;
		}
	}
}
def setDefaultNodePropertyValue(Node node, String attrName, String attrValue){
	node.setProperty(attrName, attrValue);
	//println attrName + " : " + attrValue;
}

testRun = false;   
if(!testRun){
    println ("node created, proceeding to save");
	modifiedPageList();	
}
def modifiedPageList(){
   println ("Adding page modified details at : /var/groovyconsole");     
   Node groovyNode = getNode('/var/groovyconsole');
   Node modifiedPageDetails = JcrUtils.getOrAddNode(groovyNode,'modifiedPageDetails','nt:unstructured');
   String [] pageListDetailsArray = pageListDetails.toArray(new String[0]);
   modifiedPageDetails.setProperty("imageandctagalleryList",pageListDetailsArray);
   save();
}
 
println "Total Pages Updated " + noOfPagesUpdated;