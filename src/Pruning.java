
public class Pruning {

	static String path_to_the_tree_nodes = "/export1/project/hondius/newKrakenResearch/databases/nodes.txt";
	static String output_file_path = "/export1/project/hondius/newKrakenResearch/databases/nodes_new_file.txt";
	static TreePruning tree ;
	public static void main(String[] args)
	{
		tree = new TreePruning(path_to_the_tree_nodes);
		tree.tree_pruning(tree.getNodeUsingUid(1));
		
		while(true)
		{
			tree.pruneAfterPruning(tree.getNodeUsingUid(1));
			if(tree.pruneHappened == false) break;
			tree.pruneHappened = false;
		}
		
		
		
	}
}
