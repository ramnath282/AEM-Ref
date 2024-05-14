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
legacyComponentResourceType='mattel/play/components/content/imageAndText'
enhancedContainerResourceType='mattel/ecomm/fisher-price/components/content/responsivegrid'
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
		if(jcrContentNode != null){
			NodeIterator itr=jcrContentNode.getNodes();
			iterateNode(itr, currentPagePath);
		}
	}
}

def iterateNode(NodeIterator itr,  currentPagePath){
	isParentList=false;
	String imageCardName = "card_1";
	String textCardName = "card_2";
	String ctaNodeName = "cta_1";
	
	while(itr.hasNext()){  
		Node childNode=itr.nextNode();
		if(childNode.hasProperty(resourceTypePropName) && childNode.getProperty(resourceTypePropName).getString().equals(legacyComponentResourceType) && !childNode.getProperty("imageTextWidthRatio").getString().equals("media-50-50")){
			println "Pagepath : " + childNode.getPath()
			noOfPagesUpdated =noOfPagesUpdated+1;
			pageListDetails.add(currentPagePath.toString());
			Node parentNode=childNode.getParent();

			String nodeName = childNode.getName().toString();
			String newNodeName='responsivegrid';
			if(nodeName.contains('_')){
			   newNodeName=newNodeName + '_'+nodeName.substring(nodeName.indexOf('_')+1,nodeName.length()).toString();
			}
			
			try{
			    Node containerNode = checkIfNodeExists(parentNode, newNodeName);
				parentNode.orderBefore(containerNode.getName().toString(),childNode.getName().toString());
				
				
				Node imageCardNode = checkIfNodeExists(containerNode, imageCardName);
				Node textCardNode = checkIfNodeExists(containerNode, textCardName);
				Node ctaNode = checkIfNodeExists(textCardNode, ctaNodeName);
				
				if(childNode.get("imageSectionAlignment") == "right"){
					containerNode.orderBefore(textCardNode.getName().toString(), imageCardNode.getName().toString());
					def containerNodeStyles = ["1580906808797"] as String[];
					containerNode.setProperty('cq:styleIds',containerNodeStyles);
				}else{
					def containerNodeStyles = ["1580906884784"] as String[];
					containerNode.setProperty('cq:styleIds',containerNodeStyles);
				}

				setEnhancedLayout(childNode, containerNode);
				setImageCard(childNode, imageCardNode);
				setTextCard(childNode, textCardNode);
				setCTADetails(childNode, ctaNode);
				
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

def Node checkIfNodeExists(Node parentNode, String newNodeName){
    Node newNode;
    if(parentNode.hasNode(newNodeName)){
	    newNode = parentNode.getNode(newNodeName);
    }else{
        newNode = parentNode.addNode(newNodeName);
    }
    return newNode;
}

def setEnhancedLayout(Node mainNode, Node containerNode){
	containerNode.setProperty('sling:resourceType',enhancedContainerResourceType);
	setPropertyValueToNode(mainNode, containerNode, "textBackOption", "backgroundOption");
	setPropertyValueToNode(mainNode, containerNode, "textBackgroundColor", "backgroundColor");
	setDMImageProperties(mainNode, containerNode, "textFileReferenceBgImg", "image", "smartcrop");
}

def setImageCard(Node mainNode, Node imageCardNode){
	imageCardNode.setProperty('sling:resourceType',cardResourceType);
	setDMImageProperties(mainNode, imageCardNode, "imageFileReferenceMainImageDesktop", "image", "smartcrop");
	
	String imagePath = mainNode.get("imageFileReferenceMainImageMobile").toString();
	if(imagePath != "null"){
    	String image = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.lastIndexOf("."));
    	imageCardNode.setProperty('assetIDmobileImage',environment_AssetID+image);
    	setPropertyValueToNode(mainNode, imageCardNode, "imageFileReferenceMainImageMobile", "mobileImage");
		setDefaultNodePropertyValue(imageCardNode, "isCustomMobileEnabled", "true");
	}else{
		setDefaultNodePropertyValue(imageCardNode, "isCustomMobileEnabled", "false");
	}
	
	setPropertyValueToNode(mainNode, imageCardNode, "mainImageDesktopAlt", "imageAltText");
	setPropertyValueToNode(mainNode, imageCardNode, "mainImageMobileAlt", "mobileImageAltText");
	
	setDefaultNodePropertyValue(imageCardNode, "isHoverImageEnabled", "false");
	setDefaultNodePropertyValue(imageCardNode, "entrCompClickable", "false");
	
	def imageCardNodeStyles = ["1578457920612","1578487354768"] as String[];
	imageCardNode.setProperty('cq:styleIds',imageCardNodeStyles);
}

def setTextCard(Node mainNode, Node textCardNode){
	textCardNode.setProperty('sling:resourceType',cardResourceType);
	setPropertyValueToNode(mainNode, textCardNode, "textSectionTitle", "title");
	setDMImageProperties(mainNode, textCardNode, "brandLogoImg", "image", "imagepreset");
	setPropertyValueToNode(mainNode, textCardNode, "brandLogoAlt", "imageAltText");
	setPropertyValueToNode(mainNode, textCardNode, "textSectionDescription", "description");
	
	setDefaultNodePropertyValue(textCardNode, "isCustomMobileEnabled", "false");
	setDefaultNodePropertyValue(textCardNode, "isHoverImageEnabled", "false");
	setDefaultNodePropertyValue(textCardNode, "entrCompClickable", "false");
	
	def textCardNodeStyles = ["1578487372824", "1578457920612", "1578457877107"] as String[];
	textCardNode.setProperty('cq:styleIds',textCardNodeStyles);
}

def setCTADetails(Node mainNode, Node ctaNode){
	ctaNode.setProperty('sling:resourceType',ctaResourceType);
	setPropertyValueToNode(mainNode, ctaNode, "alwaysEnglishForTitle", "trackingText");
	setPropertyValueToNode(mainNode, ctaNode, "imageTextCtaButtonText", "linkText");
	setPropertyValueToNode(mainNode, ctaNode, "imageTextCtaButtonLink", "linkURL");
		
	def ctaNodeStyles = ["1578995059742"] as String[];
	ctaNode.setProperty('cq:styleIds',ctaNodeStyles);
	
	String openCtaLinksIn = mainNode.get("imageTextCtaOption").toString();
	String gotoGridOption = mainNode.get("enableScroll").toString();
	if(gotoGridOption == "true"){
		ctaNode.setProperty("linkOptions", "gotogrid");
	}else{
		if(openCtaLinksIn == "newtab"){
			ctaNode.setProperty("linkOptions", "newTab");
		}else if(openCtaLinksIn == "newwindow"){
			ctaNode.setProperty("linkOptions", "blank");
		}else if(openCtaLinksIn == "samewindow"){
			ctaNode.setProperty("linkOptions", "self");
		}
	}
	
	setDefaultNodePropertyValue(ctaNode, "trackThisCTA", "true");
	setDefaultNodePropertyValue(ctaNode, "useInterstitial", "false");
}

def setPropertyValueToNode(Node originalNode, Node nwNode, String oldAttrName, String newAttrName){
	if(!nwNode.get(oldAttrName)) {
		if(originalNode.get(oldAttrName) != null){
		  String oldAttrValue = originalNode.get(oldAttrName).toString();
		  nwNode.setProperty(newAttrName, oldAttrValue);
		}
	}
}

def setDefaultNodePropertyValue(Node node, String attrName, String attrValue){
	node.setProperty(attrName, attrValue);
	//println attrName + " : " + attrValue;
}

def setDMImageProperties(Node referenceNode, Node newNode, String oldAttrName, String newAttrName, String presetType){
	if(referenceNode.get(oldAttrName) != null) {
		setPropertyValueToNode(referenceNode,newNode,oldAttrName,newAttrName);
		String imagePath = referenceNode.get(oldAttrName).toString();
		String image = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.lastIndexOf("."));
		newNode.setProperty('assetIDimage',environment_AssetID+image);
		
		setDefaultNodePropertyValue(newNode,"assetType", "image");
		setDefaultNodePropertyValue(newNode,"imageserverurl","https://s7d1.scene7.com/is/image/");
		setDefaultNodePropertyValue(newNode,"s7ViewerPreset","");
		setDefaultNodePropertyValue(newNode,"videoserverurl","https://s7d1.scene7.com/is/content/");
		setDefaultNodePropertyValue(newNode,"viewerPath","https://s7d1.scene7.com/s7viewers/");
		setDefaultNodePropertyValue(newNode,"viewerPresetPath","");
			
		if(presetType.equals("smartcrop")){
			setDefaultNodePropertyValue(newNode,"breakpoints","");	
			setDefaultNodePropertyValue(newNode,"s7ImagePreset","");
			setDefaultNodePropertyValue(newNode,"s7PresetType","smartCrop");
			setDefaultNodePropertyValue(newNode,"s7ViewerPresetPath","");
		}else if(presetType.equals("imagepreset")){
			setDefaultNodePropertyValue(newNode,"s7PresetType","image");
			setDefaultNodePropertyValue(newNode,"s7ViewerPresetPath","||");
		}else{
			
		}
	}
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
   modifiedPageDetails.setProperty("imageandtextctaList",pageListDetailsArray);
   save();
}
 
println "Total Pages Updated " + noOfPagesUpdated;