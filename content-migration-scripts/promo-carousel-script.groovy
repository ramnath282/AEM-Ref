import javax.jcr.Node
import javax.jcr.Node
import org.apache.jackrabbit.vault.packaging.*
import org.apache.jackrabbit.vault.fs.api.*
import org.apache.jackrabbit.vault.fs.config.*
import org.apache.jackrabbit.vault.util.*
import org.apache.jackrabbit.vault.fs.filter.*
import org.apache.sling.api.resource.ResourceResolverFactory.*
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import javax.jcr.Session.*
import java.util.*



//PromoCarousel

def testRun = true
/*Flag to count the number of pages Updated */
noOfPagesUpdated = 0

List<String> pageListDetails;

/*Pathfield which needs to be iterated for an operation*/
path='/content/fisher-price/language-masters'
propName='sling:resourceType'
legacyComponentValue='mattel/play/components/content/promoCarousel'
def Node newNode;
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
    environment_AssetID='mattelsitespreprod';    
    while(itr.hasNext()){        
		Node childNode=itr.nextNode();
		if(childNode.hasProperty('sling:resourceType') && childNode.getProperty('sling:resourceType').getString().equals('mattel/play/components/content/promoCarousel')){
			println 'Modified Page :' + currentPagePath + ':';
			pageListDetails.add(currentPagePath.toString());
			noOfPagesUpdated =noOfPagesUpdated+1; 
			Node parentNode=childNode.getParent();
			String nodeName = childNode.getName().toString();
			String newNodeName='carouselcontainer';
			if(nodeName.contains('_')){				   
				newNodeName=newNodeName + '_'+nodeName.substring(nodeName.indexOf('_')+1,nodeName.length()).toString();
			}
				  
			try{
			  // Update Node 
			 //  newNode = parentNode.getNode(newNodeName);
            //   add node
			   newNode = parentNode.addNode(newNodeName);
			   newNode.setProperty('jcr:title','carouselcontainer');
			   newNode.setProperty('sling:resourceType','mattel/ecomm/fisher-price/components/content/carouselContainer'); 
			   parentNode.orderBefore(newNode.getName().toString(),childNode.getName().toString());
				if(!newNode.get("timer")) {
				  String showMoreText = childNode.get("transitionTime").toString();
				  newNode.setProperty("timer", showMoreText);
				}
			   
			   // Set New Properties :
			   
				if(!newNode.get("autoPlay")) {
				   newNode.setProperty("autoPlay", "true");
				}
				if(!newNode.get("backgroundOption")) {
				  newNode.setProperty("backgroundOption", "color");
				}
				
				// style for mobile-text-bottom, full-bleed
				 String [] ids = ['1580458777147','1581599477561'];
				 newNode.setProperty("cq:styleIds",ids);
				
				if(!newNode.get("customMobile")) {
				   newNode.setProperty("customMobile", false);
				}
				if(!newNode.get("entrCompClickable")) {
				   newNode.setProperty("entrCompClickable", false);
				}
				if(!newNode.get("infinte")) {
				   newNode.setProperty("infinte", true);
				}
				if(!newNode.get("linkOptions")) {
				   newNode.setProperty("linkOptions", "samewindow");
				}
				if(!newNode.get("s7PresetType")) {
				   newNode.setProperty("s7PresetType", "viewer");
				}
				if(!newNode.get("s7ViewerPreset")) {
				   newNode.setProperty("s7ViewerPreset", "||");
				}
				if(!newNode.get("slideToShow")) {
				   newNode.setProperty("slideToShow", "1");
				}
				if(!newNode.get("slidetoscroll")) {
				   newNode.setProperty("slidetoscroll", "1");
				}
				if(!newNode.get("textIsRich")) {
				   String [] isRichText = ['true'];
				   newNode.setProperty("textIsRich", isRichText);
				}
				if(!newNode.get("tileImage")) {
				   newNode.setProperty("tileImage", "false");
				}
				if(!newNode.get("tileOption")) {
				   newNode.setProperty("tileOption", "rectangle");
				}
				if(!newNode.get("viewAllCtaSwitch")) {
				   newNode.setProperty("viewAllCtaSwitch", "false");
				}
				if(!newNode.get("useInterstitial")) {
				   newNode.setProperty("useInterstitial", "false");
				}
				
				if(childNode.hasNodes()){
        		iterateSlides(childNode.getNode("promcarousel"),newNode);
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
//  slides

def iterateSlides(Node childNode,Node newNode){
        int i = 1;
        NodeIterator oldSlideNodeIterator = childNode.getNodes();
        while(oldSlideNodeIterator.hasNext()){
            String newSlideNodeName = 'content';
	        Node oldSlideNode = oldSlideNodeIterator.next();
		    String oldSlideNodeName = oldSlideNode.getName();
		    newSlideNodeName=newSlideNodeName + '_'+oldSlideNodeName.substring(oldSlideNodeName.length()-1,oldSlideNodeName.length()).toString();
			try{
			         // Update Node
			        //  Node newSlideNode = newNode.getNode(newSlideNodeName);
				   //   add Node
				   Node newSlideNode = newNode.addNode(newSlideNodeName);
				   newSlideNode.setProperty('jcr:title','responsive grid');
				   newSlideNode.setProperty('sling:resourceType','mattel/ecomm/fisher-price/components/content/responsivegrid');
				     if(oldSlideNode.get('backgroundoption') != null){
					newSlideNode.setProperty('backgroundoption',oldSlideNode.get('backgroundoption').toString());
				   }
				    if(oldSlideNode.get('ctaLink') != null){
					 newSlideNode.setProperty("entrCompClickable", 'true');
					}
				   
				   newSlideNode.setProperty('tileImage','false');
				   newSlideNode.setProperty('tileOption','rectangle');
				   newSlideNode.setProperty('cq:panelTitle',i);
				   i++ ;
				   // set DM images 
				  if(oldSlideNode.get('crlImage') != null){
				   newSlideNode.setProperty('image',oldSlideNode.get('crlImage').toString());
				   String imagePath = oldSlideNode.get('crlImage').toString();
				   String image = imagePath.substring(imagePath.lastIndexOf('/'),imagePath.lastIndexOf('.'));
				   newSlideNode.setProperty('assetIDimage',environment_AssetID+image);
				  }
				  
				   if(oldSlideNode.get('imageAltText') != null){
				    newSlideNode.setProperty('imageAltText',oldSlideNode.get('imageAltText').toString());
				   }
				  
				   if(oldSlideNode.get('mobileImage') != null){
				    newSlideNode.setProperty('customMobile','true');   
				    String mobileImagePath = oldSlideNode.get('mobileImage').toString();
				    String mobileImage = mobileImagePath.substring(mobileImagePath.lastIndexOf('/'),mobileImagePath.lastIndexOf('.'));
				    newSlideNode.setProperty('assetIDmobileImage',environment_AssetID+mobileImage);
				    newSlideNode.setProperty('mobileImage',oldSlideNode.get('mobileImage').toString());
				    newSlideNode.setProperty('mobileImageAltText',oldSlideNode.get('imageAltText').toString());
				   }
				   // smart crop otions
				   newSlideNode.setProperty('assetType','image');
				   newSlideNode.setProperty('breakpoints','');
				   newSlideNode.setProperty('imageserverurl','https://s7d1.scene7.com/is/image/');
				   newSlideNode.setProperty('s7ImagePreset','');
				   newSlideNode.setProperty('s7PresetType','smartCrop');
				   newSlideNode.setProperty('s7ViewerPreset','');
				   newSlideNode.setProperty('s7ViewerPresetPath','');
				   newSlideNode.setProperty('videoserverurl','https://s7d1.scene7.com/is/content/');
				   newSlideNode.setProperty('viewerPath','https://s7d1.scene7.com/s7viewers/');
				   newSlideNode.setProperty('viewerPresetPath','');
				   
				   // add textOverlay
				   addTextOverlay(oldSlideNode,newSlideNode);
			}catch(Exception e){
				    println(e)
			}
            
        }
} 

def addTextOverlay(Node oldSlideNode,Node newSlideNode){
    
       // Update Node
      //  Node textOverlayComp = newSlideNode.getNode('textoverlaycomponent');
     //   add  node   
     Node textOverlayComp = newSlideNode.addNode('textoverlaycomponent');
	 // text-center , light-theme , text-middle
     String [] ids = ['1580972754605','1580972677204','1580972792627'];
	 textOverlayComp.setProperty("cq:styleIds",ids);
	 if(oldSlideNode.get('description') != null){
	    textOverlayComp.setProperty("description",oldSlideNode.get('description').toString());
	 }
	 if(oldSlideNode.get('title') != null){
	  textOverlayComp.setProperty("title",'<h2>' + oldSlideNode.get('title').toString() + '</h2>');
	 }
	 textOverlayComp.setProperty("entrCompClickable", false);
     textOverlayComp.setProperty('sling:resourceType','mattel/ecomm/fisher-price/components/content/textOverlayComponent');
     String [] isRichText = ['true'];
	 textOverlayComp.setProperty("textIsRich", isRichText);
	 
	 // add node for textOverlay Layout
	 Node cqResponsive = textOverlayComp.addNode('cq:responsive');
	 Node defaultNode = cqResponsive.addNode('default');
	 defaultNode.setProperty("width","5");
	// add cta n tracking details
	   // updated node
	  //  Node cta_button = textOverlayComp.getNode('cta_1');
	 //   add Node
	    Node cta_button = textOverlayComp.addNode('cta_1');
	   
	    if(oldSlideNode.get('ctaLink') != null){
	        cta_button.setProperty("linkURL", oldSlideNode.get('ctaLink').toString()); 
	        String [] ctaIds = ['1578995059742'];
	        cta_button.setProperty("cq:styleIds",ctaIds);
	    }
	     if(oldSlideNode.get('ctaLabel') != null){
	        cta_button.setProperty("linkText", oldSlideNode.get('ctaLabel').toString());
	     }
	    if(oldSlideNode.get('clrTargetUrl') != null){
	        String linkOptions = "blank";
	        if(StringUtils.equals(oldSlideNode.get('clrTargetUrl').toString(),'tabWindow')){
	            linkOptions = "newTab";
	        }else if(StringUtils.equals(oldSlideNode.get('clrTargetUrl').toString(),'newWindow')){
	            linkOptions = "blank";
	        }else if(StringUtils.equals(oldSlideNode.get('clrTargetUrl').toString(),'sameWindow')){
	            linkOtions = "self";  
	        } 
	        cta_button.setProperty("linkOptions",linkOtions);
	    }  
	    cta_button.setProperty('sling:resourceType','mattel/ecomm/fisher-price/components/content/ctaItem');
	    cta_button.setProperty("textIsRich", isRichText);
	 if(oldSlideNode.get('awalysEnglish') != null || oldSlideNode.get('adobeTrackingForCta') != null){
	     cta_button.setProperty("trackThisCTA",true);
	    if(oldSlideNode.get('awalysEnglish') != null && oldSlideNode.get('adobeTrackingForCta') != null){
	         cta_button.setProperty("trackingText", oldSlideNode.get('awalysEnglish').toString() + '|'+ oldSlideNode.get('adobeTrackingForCta').toString());
	    }else if(oldSlideNode.get('awalysEnglish') != null){
	         cta_button.setProperty("trackingText", oldSlideNode.get('awalysEnglish').toString());
	    }else if(oldSlideNode.get('adobeTrackingForCta') != null){
	         cta_button.setProperty("trackingText", oldSlideNode.get('adobeTrackingForCta').toString());
	    }
	 }
	  cta_button.setProperty("viewAllCtaSwitch", "false");
	  cta_button.setProperty("useInterstitial", "false");
	 
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
   modifiedPageDetails.setProperty("promoCarouselPageList",pageListDetailsArray);
   save();

} 

println ("Is it Test Run : "  + testRun);
println ("Total Number of  Pages Updated  :" + noOfPagesUpdated);