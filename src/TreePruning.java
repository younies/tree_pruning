import java.io.*;
import java.util.*;

public class TreePruning
{
  
	ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
	ArrayList<Long> neededIdsList = new ArrayList<Long>();
	ArrayList<Long> uidsThatTaken = new ArrayList<Long>();
	private Scanner scanner;
	boolean pruneHappened = false;
	PrintWriter output;
  
  public TreePruning(String path) throws FileNotFoundException
  {
	  scanner = new Scanner( new File( path));
	  int nn = 1;
	  while(scanner.hasNextLong())
	  {
		  System.out.println(nn++);
		  long uid =  scanner.nextLong();
		  long parentUid = scanner.nextLong();
		  scanner.nextLine();
		  
		  TreeNode node = new TreeNode(uid , parentUid);//constructing the node
		  this.treeNodes.add(node);
	  }
	  
    scanner.close();
    
    
    
  }
  
  
  
  public void connectNodes()// this 
  {
	  for(TreeNode node: this.treeNodes)
	  {
		  TreeNode parent = getNodeUsingUid(node.parentUid);
		  if(parent.uid != node.uid)
			  parent.children.add(node);
	  }
  }
  
  
  public TreeNode getNodeUsingUid(long uid)
  {
	  int start = 0 , end = this.treeNodes.size() - 1 , mid;
	  
	  while(end >= end)
	  {
		  mid = (start + end) / 2;
		  if(this.treeNodes.get(mid).uid == uid)
			  return this.treeNodes.get(mid);
		  if(this.treeNodes.get(mid).uid > uid)
			  end = mid - 1;
		  else
			  start = mid + 1;
	  }
	  
	  System.out.println(uid);
	  System.out.println("Null");
	  
	  return null;
  }
  
  
  public void tagTreeNodes(String path) throws FileNotFoundException // this for tagging all the tree elements that we need
  {
	  scanner = new Scanner(new File( path ));
	  
	  //read the elements
	  while(scanner.hasNextLong())
	  {
		  neededIdsList.add(scanner.nextLong());
	  }
	  scanner.close();
	  
	  Collections.sort(neededIdsList);//sorting the ids
	  
	  for(TreeNode node: this.treeNodes)
	  {
		  node.tag = (Collections.binarySearch(neededIdsList , node.uid) >= 0);
		  if(node.tag) this.uidsThatTaken.add(node.uid);
	  }
	  
	  Collections.sort(uidsThatTaken);
  }
 
 

  public void tree_pruning(TreeNode node)//pruin the tree
  {
	  if(node == null || node.children == null) return;
	  for(TreeNode tempNode : node.children)
		  tree_pruning( tempNode);
	  
	  if(node.children.size() == 0 && node.tag == false)
	  {
		  TreeNode parent = this.getNodeUsingUid(node.parentUid);
		  parent.removeChild(node); 
		  return;
	  }
	  return;	 
  }
  
  
  public void pruneAfterPruning(TreeNode node)
  {
	  if(node.children.size() == 1 && node.tag == false)
	  {
		  this.pruneHappened = true;
		  TreeNode child = node.children.get(0);
		  TreeNode parent =  this.getNodeUsingUid(node.parentUid);
		  parent.removeChild(node);
		  child.parentUid = parent.uid;
		  parent.children.add(child);
		  pruneAfterPruning(child);
		  return;
	  }
	  
	  for(TreeNode child : node.children)
	  {
		  pruneAfterPruning(child);
	  }
	  
	  return;
  }
  
  
  private boolean printAllTheNodeInFile(TreeNode node)
  {
	  //print the uid
	  this.output.print(node.uid);
	  this.output.print("\t");
	  
	  //print the parent uid
	  this.output.print(node.parentUid);
	  this.output.print("\t");
	  
	  
	  for(TreeNode child: node.children)
	  {
		  printAllTheNodeInFile( child);
	  }
	  
	  return true;
  }
  
  public boolean printAllTheNodeInFile(String output_file)
  {
	  try
	  {
		  output = new PrintWriter(new FileWriter(output_file));
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.toString());
	  }
	  
	  
	  return printAllTheNodeInFile(this.getNodeUsingUid(1));
	  
  }
}
  

