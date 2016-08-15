import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Pruning {

	static String path_to_the_tree_nodes = "/export1/project/hondius/newKrakenResearch/databases/nodes.txt";
	static String path_to_the_needed_nodes  = "/export1/project/hondius/newKrakenResearch/databases/uids_new.txt"; 
	static String output_file_path = "/export1/project/hondius/newKrakenResearch/finalNewTree.txt";
	static String output_needed = "/export1/project/hondius/newKrakenResearch/finalSynchronizedNodeUIDs.txt";

	static TreePruning tree ;
	public static void main(String[] args) throws Exception
	{
		tree = new TreePruning(path_to_the_tree_nodes);
		tree.tagTreeNodes(path_to_the_needed_nodes);
		tree.tree_pruning(tree.getNodeUsingUid(1) );
		
		while(true)
		{
			tree.pruneAfterPruning(tree.getNodeUsingUid(1));
			if(tree.pruneHappened == false) break;
			tree.pruneHappened = false;
		}
		
		tree.givingShortNames(tree.getNodeUsingUid(1));
		
		tree.printAllTheNodeInFile(output_file_path );
		
		tree.output.flush();
		tree.output.close();
		tree.printTheDatabaseNames(output_needed);
		

		System.out.println("Congratulations!!!  the new tree was created :D  ");
		System.out.println("The path to the new tree: " +output_file_path);
		System.out.println("The path to the synchronized nodes: " + output_needed);
	}
}