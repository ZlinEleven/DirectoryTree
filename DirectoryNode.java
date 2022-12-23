/**
 * This class represents a DirectoryNode object which is a node in a DirectoryTree object
 */
public class DirectoryNode{
    private String name;
    private DirectoryNode left;
    private DirectoryNode middle;
    private DirectoryNode right;
    private DirectoryNode parent;
    private boolean isFile;

    /**
     * Contructor for a DirectoryNode object. All child references are set to null upon initialization
     * @param name The name of the node
     * @param isFile Whether the node represents a folder or file
     * @param parent The parent of the node, that is, the current node is a child of parent
     */
    public DirectoryNode(String name, boolean isFile, DirectoryNode parent){
        this.name = name;
        left = null;
        middle = null;
        right = null;
        this.isFile = isFile;
        this.parent = parent;
    }

    /**
     * @return The name of the node
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the node to name
     * @param name The string to set the name of the node to
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return The left child of the referenced node
     */
    public DirectoryNode getLeft(){
        return left;
    }

    /**
     * Sets the left child reference of the current node to node
     * @param node The node to set the left child reference of the current node to
     */
    public void setLeft(DirectoryNode node){
        left = node;
    }

    /**
     * @return The middle child of the referenced node
     */
    public DirectoryNode getMiddle(){
        return middle;
    }

    /**
     * Sets the middle child reference of the current node to node
     * @param node The node to set the middle child reference of the current node to
     */
    public void setMiddle(DirectoryNode node){
        middle = node;
    }

    /**
     * @return The right child of the referenced node
     */
    public DirectoryNode getRight(){
        return right;
    }

    /**
     * Sets the right child reference of the current node to node
     * @param node The node to set the right child reference of the current node to
     */
    public void setRight(DirectoryNode node){
        right = node;
    }

    /**
     * @return The parent of the current node
     */
    public DirectoryNode getParent(){
        return parent;
    }

    /**
     * Sets the parent reference of the current node to node
     * @param node The node to set the parent reference of the current node to
     */
    public void setParent(DirectoryNode node){
        parent = node;
    }

    /**
     * Whether the current node is a file or not
     * @return True if it's a file, false otherwise
     */
    public boolean getIsFile(){
        return isFile;
    }

    /**
     * Attempts to add a DirectoryNode to the left-most available child reference of the current node
     * @param node The node to be referenced by the left-most available child reference
     * @throws NotADirectoryException If the DirectoryNode that node is being attempted to be added to is a file, throw an exception
     * @throws FullDirectoryException If the DirectoryNode that node is being attempted to be added to is full, throw an exception
     */
    public void addChildNode(DirectoryNode node) throws NotADirectoryException, FullDirectoryException{
        if(isFile){
            throw new NotADirectoryException("Cursor is not a directory.");
        }
        else{
            if(getLeft() == null){
                left = node;
            }
            else if(getMiddle() == null){
                middle = node;
            }
            else if(getRight() == null){
                right = node;
            }
            else{
                throw new FullDirectoryException("ERROR: Present directory is full.");
            }
        }
    }
}