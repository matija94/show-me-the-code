package trees_graphs;

public class FirstCommonAncestor {

	
	public static <T> BinaryNode<T> fca(BinaryNode<T> a, BinaryNode<T> b) {
		BinaryNode<T> parenta = a.parent;
		BinaryNode<T> parentb = b.parent;
		if (parenta == null || parentb == null) {
			return null;
		}
		
		if(parenta!=parentb) {
			int levela = level(parenta);
			int levelb = level(parentb);
			while(levela>levelb) {
				parenta=parenta.parent;
				levela--;
			}
			while(levelb>levela) {
				parentb=parentb.parent;
				levelb--;
			}
			while((parenta.parent != null || parentb.parent != null) && parenta != parentb) {
				if (parenta.parent != null) {
					parenta = parenta.parent;
				}
				if (parentb.parent != null) {
					parentb = parentb.parent;
				}
			}
		}
		
		return parenta;
	}
	
	private static <T> int level(BinaryNode<T> a) {
		if (a.parent==null) {
			return 0;
		}
		return level(a.parent)+1;
	}
	
}
