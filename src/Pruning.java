
public class Pruning {

	static String path_to_the_tree_nodes = "";
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
