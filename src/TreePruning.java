import java.io.*;
import java.util.*;

public class TreePruning
{
  
	ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
	ArrayList<Long> neededIdsList = new ArrayList<Long>();
	ArrayList<Long> uidsThatTaken = new ArrayList<Long>();
	ArrayList<Long> uidsNotTakenNotUsed = new ArrayList<Long>();
	private Scanner scanner;
	boolean pruneHappened = false;
	PrintWriter output;
	private short indexer = 0;
	TreeNode root;
  
	
  public TreePruning(String path) throws FileNotFoundException
  {
	  scanner = new Scanner( new File( path));
	  while(scanner.hasNextLong())
	  {
		  //System.out.println(nn++);
		  long uid =  scanner.nextLong();
		  long parentUid = scanner.nextLong();
		  scanner.nextLine();
		  
		  TreeNode node = new TreeNode(uid , parentUid);//constructing the node
		  this.treeNodes.add(node);
	  }
	  
    scanner.close();
    
    connectNodes();
    
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
	  System.out.println("Taken");
	  System.out.println(uidsThatTaken.size());
	  
	  for(long uid: neededIdsList )
		  if(Collections.binarySearch(uidsThatTaken, uid) < 0 )
			  this.uidsNotTakenNotUsed.add(uid);
  }
 
 

  public void tree_pruning(TreeNode node)//pruin the tree
  {
	//  if(node == null || node.children == null) return;
	  TreeNode childOfNode[] = new TreeNode[node.children.size()];
	  int i = 0;
	  for(TreeNode tempNode : node.children)
		  childOfNode[i++] = tempNode;
	  
	  for( TreeNode tempNode : childOfNode )
		  tree_pruning(tempNode);
	  
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
	  TreeNode childOfNode[] = new TreeNode[node.children.size()];
	  int i = 0;
	  for(TreeNode tempNode : node.children)
		  childOfNode[i++] = tempNode;
	  
	  for(TreeNode child : childOfNode)
	  {
		  pruneAfterPruning(child);
	  }
	  
	  return;
  }
  
  private boolean printAllTheNodeInFile(TreeNode node , short parentShortName) throws Exception
  {
	  if(node == null) throw new Exception("null node");
	  //print the uid
	  this.output.print(node.uid);
	  this.output.print("\t");
	  
	  //print the parent uid
	  this.output.print(node.parentUid);
	  this.output.print("\t");
	  
	  //print new index
	  this.output.print(node.shortName);
	  this.output.print("\t");
	  
	  this.output.print(parentShortName);
	  this.output.print("\t");
	  
	  if(node.tag)
		  this.output.print(1);
	  else
		  this.output.print(0);
	  
	  this.output.println("\t");
	  
	  //print children
	  
	  for(TreeNode child: node.children)
	  {
		  printAllTheNodeInFile( child , node.shortName);
	  }
	  
	  return true;
  }
  
  
  
  public boolean printAllTheNodeInFile(String output_file) throws Exception
  {
	  try
	  {
		  output = new PrintWriter(new FileWriter(output_file));
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.toString());
	  }
	  
	   
	  return printAllTheNodeInFile(this.getNodeUsingUid(1) , (short)0 );
	  
  }
  
  
  public void givingShortNames(TreeNode node)
  {
	  Queue <TreeNode> breadthFirst = new LinkedList<>();
	  breadthFirst.add(node);
	  
	  while(!breadthFirst.isEmpty())
	  {
		  TreeNode temp = breadthFirst.poll();
		  temp.shortName = this.indexer++;
		  for(TreeNode child: temp.children)
			  breadthFirst.add(child);
	  }
	  this.indexer = 0;
  }
  
  public ArrayList<Long> getNoNUsedUIDS()
  {
	   return this.uidsNotTakenNotUsed;   
  }
  
  public void printTheDatabaseNames(String path)
  {
	  try
	  {
		  output = new PrintWriter(new FileWriter(path));
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.toString());
	  }
	  
	  for(  TreeNode node : treeNodes)
	  {
		  if(node.tag)
			  this.output.println(node.uid);
	  }
	  this.output.flush();
	  this.output.close();
  }
}