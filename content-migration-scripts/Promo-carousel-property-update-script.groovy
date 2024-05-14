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

path='/content/fisher-price/language-masters'
propName='sling:resourceType'
noOfNodesUpdated = 0
modifyProperty()

def modifyProperty(){
    getPage(path).recurse
    {
        page ->
        def jcrContentNode = page.node;
        String currentPagePath=page.getPath();
        NodeIterator itr=jcrContentNode.getNodes();
        iterateNode(itr, currentPagePath);
    }
}

def iterateNode(NodeIterator itr,String currentPagePath){
    while(itr.hasNext()){
    		Node childNode =  itr.nextNode();
    		if(childNode.hasProperty('sling:resourceType') && childNode.getProperty('sling:resourceType').getString().equals('mattel/ecomm/fisher-price/components/content/carouselContainer')){  
    			noOfNodesUpdated =noOfNodesUpdated+1;
    			try{
        			    if(childNode.hasNodes()){
        			        iterateSlides(childNode,currentPagePath);
        			    }
    			    }catch(Exception e){
    			        println(e)
    			    }    
    		}
    	if(childNode.hasNodes()){
					 NodeIterator inrItr=childNode.getNodes(); 
					 iterateNode(inrItr ,currentPagePath);
		}
    }
}

def iterateSlides(Node childNode,String currentPagePath){
    NodeIterator slideNodeItr = childNode.getNodes();
    while(slideNodeItr.hasNext())
    {
        Node slide = slideNodeItr.nextNode();
        if(slide.hasProperty('sling:resourceType') && slide.getProperty('sling:resourceType').getString().equals('mattel/ecomm/fisher-price/components/content/responsivegrid')){
            if(slide.hasNodes()){
                NodeIterator textOverlayItr = slide.getNodes();
                while(textOverlayItr.hasNext()){
                    Node textOverlayNode = textOverlayItr.nextNode();
                    if(textOverlayNode.hasProperty('sling:resourceType') && textOverlayNode.getProperty('sling:resourceType').getString().equals('mattel/ecomm/fisher-price/components/content/textOverlayComponent')){
                        if(textOverlayNode.hasNodes()){
                            NodeIterator childNodeItr = textOverlayNode.getNodes();
                            while(childNodeItr.hasNext()){
                                Node childLayoutNode = childNodeItr.nextNode();
                                if(childLayoutNode.hasNodes()){
                                  NodeIterator defaultNodeIterator = childLayoutNode.getNodes();
                                  while(defaultNodeIterator.hasNext()){
                                      Node defaultNode = defaultNodeIterator.nextNode();
                                      if(defaultNode.getProperty('width') != null){
                                          println 'path' + defaultNode.getPath();
                                          defaultNode.setProperty('width','4');
                                          defaultNode.setProperty('offset','1');
                                          rollout(currentPagePath);
                                      }
                                  }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}  

 def rollout(String currentPagePath){
    Page page = getPage(currentPagePath); 
    RolloutManager.RolloutParams params = new RolloutManager.RolloutParams();
    params.isDeep = false;
    params.reset = false;
    params.trigger = RolloutManager.Trigger.ROLLOUT;
    params.master = page;
    def rolloutManagerService = getService("com.day.cq.wcm.msm.api.RolloutManager")
       println 'Rolling out' + page.getPath() 
       rolloutManagerService.rollout(params);
      save();
}
 
println 'Nodes Updated' + noOfNodesUpdated;