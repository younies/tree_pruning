import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Pruning {

	static String path_to_the_tree_nodes = "/export1/project/hondius/newKrakenResearch/databases/nodes.txt";
	static String path_to_the_needed_nodes  = "/export1/project/hondius/newKrakenResearch/databases/uids_new.txt"; 
	static String output_file_path = "/export1/project/hondius/newKrakenResearch/newTree.txt";
	static String output_needed = "/export1/project/hondius/newKrakenResearch/synchronizedNodeUIDs.txt";

	static TreePruning tree ;
	public static void main(String[] args) throws Exception
	{
		tree = new TreePruning(path_to_the_tree_nodes);
		tree.tagTreeNodes(path_to_the_needed_nodes);
		tree.tree_pruning(tree.getNodeUsingUid(1));
		
		while(true)
		{
			tree.pruneAfterPruning(tree.getNodeUsingUid(1));
			if(tree.pruneHappened == false) break;
			tree.pruneHappened = false;
		}
		
		tree.givingShortNames(tree.getNodeUsingUid(1));
		
		tree.printAllTheNodeInFile(output_file_path);
		
		tree.output.flush();
		tree.output.close();
		tree.printTheDatabaseNames(output_needed);
		
		ArrayList<Long> nums = tree.uidsNotTakenNotUsed;
		for(long num: nums)
		{
			System.out.println(num);
		}
		
		System.out.println(nums.size());
		
		TreeNode tempNode = tree.getNodeUsingUid(272844);
		System.out.println(tempNode.tag);
		System.out.println(tempNode.uid);
		System.out.println(tempNode.parentUid);
		System.out.println(tempNode.shortName);
		
		
		
	}
}