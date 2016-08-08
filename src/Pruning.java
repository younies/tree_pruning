import java.io.FileNotFoundException;

public class Pruning {

	static String path_to_the_tree_nodes = "/export1/project/hondius/newKrakenResearch/databases/nodes.txt";
	static String path_to_the_needed_nodes  = "/export1/project/hondius/newKrakenResearch/databases/uids_new.txt"; 
	static String output_file_path = "/export1/project/hondius/newKrakenResearch/nodes222.txt";
	static TreePruning tree ;
	public static void main(String[] args) throws FileNotFoundException
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
		
		tree.printAllTheNodeInFile(output_file_path);
		
		Long notUsed[] = tree.getNoNUsedUIDS();
		
		for(long num: notUsed)
		{
			System.out.println(num);
		}
		
		System.out.println(notUsed.length);
	}
}
