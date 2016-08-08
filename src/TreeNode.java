import java.util.*;

class TreeNode
  {
    public long uid;
    public 	long parentUid;
    public  List<TreeNode> children;
    public  boolean tag = false;
    
    public TreeNode(long uid , long parentUid) //any node should has its and its parent's ids
    {
    		this.uid = uid;
    		this.parentUid = parentUid;
    		children = new ArrayList<TreeNode>();
    }
    
    
    public void removeChild(TreeNode node)
    {
    		int index = getIndexInLinkedList( node );
    		this.children.remove(index);
    }
    
    
    public int getIndexInLinkedList(TreeNode node )
    {
  	  int i = 0;
  	  for(TreeNode temp : this.children)
  	  {
  		  if(temp.uid == node.uid)
  			  return i;
  		  ++i;
  	  }
  	  return -1;
    }
    
    
  }
