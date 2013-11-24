package test;


	import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

	public class TreeSetTest {
	   public static void main(String[] args) {
	     // creating a TreeSet 
	     TreeSet <Integer>treeone = new TreeSet<Integer>();
	     TreeSet <Integer>treetwo = new TreeSet<Integer>();
	     HashSet <Integer>set = new HashSet<Integer>();
	     
	     // adding in the treeone
	     treeone.add(12);
	     treeone.add(13);
	     treeone.add(14);
	     
	     // adding in the treetwo
	     treetwo.add(17);
	     treetwo.add(15);
	     treetwo.add(16);
	     
	  // adding in the set
	     set.add(12);
	     set.add(15);
	     set.add(16);  
	     
	     // adding treetwo to treeone
//	     treeone.addAll(treetwo);
	     treeone.addAll(set);
	     
	     // create an iterator
	     Iterator iterator;
	     iterator = treeone.iterator();
	     
	     // displaying the Tree set data
	     System.out.print("Tree set data: ");     
	     while (iterator.hasNext()){
	        System.out.print(iterator.next() + " ");
	     }
	   }    
	}

