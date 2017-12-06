import java.io.*;
import java.util.*;               // for Stack class

// bottomUpApp Start-------------------------------------------
class BottomUpApp
{
  public static void main(String[] args) throws IOException
  {
    BottomUp bup;
    Tree theTree = null;
    int value;
    String str;
    
    while(true)
    {
      System.out.print("Enter first letter of balanced, ");
      System.out.print("unbalanced, show, or traverse: ");
      int choice = getChar();
      switch(choice)
      {
        case 'b':
          System.out.print("Enter string: ");
          str = getString();
          bup = new BottomUp(str);
          bup.balanced();
          theTree = bup.getTree();
          break;
        case 'u':
          System.out.print("Enter string: ");
          str = getString();
          bup = new BottomUp(str);
          bup.unbalanced();
          theTree = bup.getTree();
          break;
        case 's':
          theTree.displayTree();
          break;
          
        case 't':
          System.out.print("Enter type 1, 2 or 3: ");
          value = getInt();
          theTree.traverse(value);
          break;
        default:
          System.out.print("Invalid entry\n");
      }  // end switch
    }  // end while
  }  // end main()
 
  
  public static String getString() throws IOException
  {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    String s = br.readLine();
    return s;
  }
  public static char getChar() throws IOException
  {
    String s = getString();
    return s.charAt(0);
  }
  public static int getInt() throws IOException
  {
    String s = getString();
    return Integer.parseInt(s);
  }
}  // end class BottomUpApp
// bottomUpApp end

// Class Node start----------------------------------------------------------
class Node
{
  public char ch;                // letter
  public Node leftChild;         // this node's left child
  public Node rightChild;        // this node's right child
  Node(char c)                   // constructor
  { ch = c; }
  public void displayNode()      // display ourself
  { System.out.print(ch); }
}  // end class Node
// Class Node end

// Class Tree start---------------------------------------------------------
class Tree
{
  public Node root;              // first node of tree
  public Tree(Node nd)           // constructor
  { root = nd; }              // root of tree is arg's node
  public void traverse(int traverseType)
  {
    switch(traverseType)
    {
      case 1: System.out.print("\nPreorder traversal: ");
      preOrder(root);
      break;
      case 2: System.out.print("\nInorder traversal:  ");
      inOrder(root);
      break;
      case 3: System.out.print("\nPostorder traversal: ");
      postOrder(root);
      break;
    }
    System.out.println();
  }
  private void preOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      localRoot.displayNode();
      preOrder(localRoot.leftChild);
      preOrder(localRoot.rightChild);
    }
  }
  private void inOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      inOrder(localRoot.leftChild);
      localRoot.displayNode();
      inOrder(localRoot.rightChild);
    }
  }
  private void postOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      postOrder(localRoot.leftChild);
      postOrder(localRoot.rightChild);
      localRoot.displayNode();
    }
  }
  public void displayTree()
  {
    Stack globalStack = new Stack();
    globalStack.push(root);
    int nBlanks = 32;
    boolean isRowEmpty = false;
    System.out.println(
                       "......................................................");
    while(isRowEmpty==false)
    {
      Stack localStack = new Stack();
      isRowEmpty = true;
      
      for(int j=0; j<nBlanks; j++)
        System.out.print(' ');
      
      while(globalStack.isEmpty()==false)
      {
        Node temp = (Node)globalStack.pop();
        if(temp != null)
        {
          temp.displayNode();
          localStack.push(temp.leftChild);
          localStack.push(temp.rightChild);
          
          if(temp.leftChild != null ||
             temp.rightChild != null)
            isRowEmpty = false;
        }
        else
        {
          System.out.print("-");
          localStack.push(null);
          localStack.push(null);
        }
        for(int j=0; j<nBlanks*2-1; j++)
          System.out.print(' ');
      }  // end while globalStack not empty
      System.out.println();
      nBlanks /= 2;
      while(localStack.isEmpty()==false)
        globalStack.push( localStack.pop() );
    }  // end while isRowEmpty is false
    System.out.println(
                       "......................................................");
  }  // end displayTree()
}  // end class Tree

// class BottomUp start-------------------------------------------------------------
class BottomUp
{
  private String inString;
  private int strlen;
  private Tree[] treeArray;
  private Tree aTree;
  BottomUp(String s)                    // constructor
  {
    inString = s;
    strlen = inString.length();
    treeArray = new Tree[100];
    
    for(int j=0; j<strlen; j++)        // put characters
    {                               // in nodes, nodes
      char ch = inString.charAt(j);   // in trees, trees
      Node aNode = new Node(ch);      // in array
      treeArray[j] = new Tree(aNode);
    }
  }  // end constructor
  public Tree getTree()                 // returns final tree
  { return aTree; }
  public void unbalanced()              //makes unbalanced tree
  {
    for(int j=0; j<strlen-1; j++)
    {
      Tree tree1 = treeArray[j];      // get first subtree
      Tree tree2 = treeArray[j+1];    // get second subtree
      
      Node aNode = new Node('+');     // make node with '+'
      aTree = new Tree(aNode);        // make new tree, with
      aTree.root.leftChild = tree1.root;  // tree1 and tree2
      aTree.root.rightChild = tree2.root; // as children
      treeArray[j+1] = aTree;         // replace tree2
    }  // end for
  }  // end unbalanced()
  public void balanced()                // makes balanced tree
  {
    int nTrees = strlen;
    int j = 0;
    while(nTrees > 1)                  // until only one tree
    {
      while( treeArray[j]==null)       //j<strlen-1 
        j++;                           // skip over nulls
      if(j > strlen-2)                 // end of array?
      {
        j = 0;
        continue;
      }
      Tree tree1 = treeArray[j];      // get first subtree
      int k = j++;
      while( treeArray[j] == null && j<strlen-1 )
        j++;                         // skip over nulls
      treeArray[k] = null;            // kill old tree1
      nTrees--;                       // one less tree
      Tree tree2 = treeArray[j];      // get second subtree
      Node aNode = new Node('+');     // make node with '+'
      aTree = new Tree(aNode);        // make new tree, with
      aTree.root.leftChild = tree1.root;  // tree1 and tree2
      aTree.root.rightChild = tree2.root; // as children
      treeArray[j++] = aTree;         // replace tree2
    }  // end while
  }  // end balanced()
}  // end class BottomUp