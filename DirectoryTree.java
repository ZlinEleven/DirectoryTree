/**
 * This class represents a DirectoryTree object which is a ternary tree modeling a directory file system
 */
public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    /**
     * Constructor for a DirectoryTree object. Upon initilization, a root object is created with name root and isFile set to false
     */
    public DirectoryTree(){
        root = new DirectoryNode("root", false, null);
        cursor = root;
    }

    /**
     * @return The node referenced by the cursor variable
     */
    public DirectoryNode getCursor(){
        return cursor;
    }

    /**
     * Sets the cursor reference to root
     */
    public void resetCursor(){
        cursor = root;
    }

    /**
     * Set the reference of cursor to node
     * @param node The node cursor will reference to
     */
    public void setCursor(DirectoryNode node){
        cursor = node;
    }

    /**
     * Searches through the ternary tree looking for a node where node.getName().equals(name) is true. If the node exists and the isFile is false, the cursor reference will be be set to that node
     * @param name The name of the node to be looked for
     * @throws NotADirectoryException If the node is found and its isFile variable is true, throw an error
     */
    public void changeDirectory(String name) throws NotADirectoryException{
        if(!changeDirectoryHelper(name, root)){
            System.out.println("ERROR: No such directory named '" + name + "'." );
        }
    }

    /**
     * A helper method for the changeDirectory method. Recursively checks the ternary tree for a node with name name
     * @param name The sting variable to look for in a node's name varialbe
     * @param root The node that is being checked
     * @return If root has the same name as the name parameter, set cursor to root and return true, false otherwise
     * @throws NotADirectoryException If a node is found where root.getname().equals(name) is true and the isFile variable is also true, throw an exception
     */
    public boolean changeDirectoryHelper(String name, DirectoryNode root) throws NotADirectoryException{
        if(root == null){
            return false;
        }
        if(root.getIsFile()){
            if(root.getName().equals(name)){
                throw new NotADirectoryException("ERROR: Cannot change directory into a file.");
            }
            else{
                return false;
            }
        }
        if(root.getName().equals(name)){
            cursor = root;
            return true;
        }

        return changeDirectoryHelper(name, root.getLeft()) || changeDirectoryHelper(name, root.getMiddle()) || changeDirectoryHelper(name, root.getRight());
    }

    /**
     * Gives a string representation of the path from root to target with each level separated with a "/"
     * @param target The string to compare to each node's name variable
     * @return The string representation of the path from root to target
     */
    public String presentWorkingDirectory(String target){
        DirectoryNode pointer = root;
        String ans = pointer.getName();

        while(!pointer.getName().equals(target)){
            if(presentWorkingDirectoryHelper(pointer.getLeft(), target)){
                pointer = pointer.getLeft();
            }
            else if(presentWorkingDirectoryHelper(pointer.getMiddle(), target)){
                pointer = pointer.getMiddle();
            }
            else{
                pointer = pointer.getRight();
            }
            ans += "/" + pointer.getName();
        }
        return ans;
    }

    /**
     * A helper method for the presentWorkingDirectory method
     * @param root The node to compared the target parameter with
     * @param target The string to look for in the root's name variable
     * @return If root is a null reference, return false. If root.getName().equals(target), return true. Otherwise, recurseively check the children of root
     */
    public boolean presentWorkingDirectoryHelper(DirectoryNode root, String target){
        if(root == null){
            return false;
        }
        if(root.getName().equals(target)){
            return true;
        }
        
        return presentWorkingDirectoryHelper(root.getLeft(), target) || presentWorkingDirectoryHelper(root.getMiddle(), target) || presentWorkingDirectoryHelper(root.getRight(), target);

    }

    public String listDirectory(){
        String ans = "";
        if(cursor.getLeft() != null){
            ans += cursor.getLeft().getName() + " ";
        }
        if(cursor.getMiddle() != null){
            ans += cursor.getMiddle().getName() + " ";
        }
        if(cursor.getRight() != null){
            ans += cursor.getRight().getName() + " ";
        }
        return ans;
    }

    /**
     * Prints the DirectoryTree from root onwards with indents representing the depth of each node
     * @param root The node to start printing the DirectoryTree. Anything not within the references of root's children will not be printed
     */
    public void printDirectoryTree(DirectoryNode root){
        printDirectoryTreeHelper(root, 0);
    }

    /**
     * Helper method for the printDirectoryTree method. Recursively traverses through the ternary tree to print out each node
     * @param root The node to be printed
     * @param depth The depth from the orginal root 
     */
    public void printDirectoryTreeHelper(DirectoryNode root, int depth){
        if(root == null){
            return;
        }
        if(root.getIsFile()){
            System.out.println(("    ".repeat(depth)) + "- " + root.getName());
            return;
        }
        System.out.println(("    ".repeat(depth)) + "|- " + root.getName());
        printDirectoryTreeHelper(root.getLeft(), depth + 1);
        printDirectoryTreeHelper(root.getMiddle(), depth + 1);
        printDirectoryTreeHelper(root.getRight(), depth + 1);

    }

    /**
     * Initializes a DirectoryNode object with name set to name, isFile set to false, and parent to the node referenced by the cursor. 
     * Sets the newly initialized node to the left-most available child reference for the cursor
     * @param name The name to set the new to
     * @throws IllegalArgumentException If name contains any spaces or slashes, throw an error
     * @throws NotADirectoryException If cursor is not a directory (isFile is true), throw an error
     * @throws FullDirectoryException If all three of cursor's children references are not null, throw an error
     */
    public void makeDirectory(String name) throws IllegalArgumentException, NotADirectoryException, FullDirectoryException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("Directory name should not contain spaces or forward slashes.");
        }
        DirectoryNode directory = new DirectoryNode(name, false, cursor);
        cursor.addChildNode(directory);
    }

    /**
     * Initializes a DirectoryNode object with name set to name, isFile set to truez, and parent to the node referenced by the cursor. 
     * Sets the newly initialized node to the left-most available child reference for the cursor
     * @param name The name to set the new to
     * @throws IllegalArgumentException If name contains any spaces or slashes, throw an error
     * @throws NotADirectoryException If cursor is not a directory (isFile is true), throw an error
     * @throws FullDirectoryException If all three of cursor's children references are not null, throw an error
     */
    public void makeFile(String name) throws IllegalArgumentException, NotADirectoryException, FullDirectoryException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("File name should not contain spaces or forward slashes.");
        }

        DirectoryNode file = new DirectoryNode(name, true, cursor);
        cursor.addChildNode(file);
    }

    /**
     * Prints out the file path of every occurrence of a node with name name
     * @param name The string to look for in each node's name variable
     * @return The number of occurrences of nodes where root.getName().equals(name) is true
     */
    public int findFile(String name){
        return findFileHelper(root, "", name, 0);
    }

    /**
     * Helper method to findFile that recursively checks every node in the ternary tree for nodes with name variable as target
     * @param root The node that is being checked
     * @param path The path from absolute root to the parameter root with each node separated with a slash
     * @param target The string to compare root.getName() to
     * @param count A counter for the number of occurrences of a node with name target
     * @return 0 if root is null, 1 if root.getName().equals(target). Otherwise, recursively traverse through all the child of root and return count
     */
    public int findFileHelper(DirectoryNode root, String path, String target, int count){
        if(root == null){
            return 0;
        }
        if(root.getName().equals(target)){
            System.out.println(path.substring(1) + "/" + target);
            return 1;
        }
        if(!root.getIsFile()){
            count += findFileHelper(root.getLeft(), path + "/" + root.getName(), target, count);
            count += findFileHelper(root.getMiddle(), path + "/" + root.getName(), target, count);
            count += findFileHelper(root.getRight(), path + "/" + root.getName(), target, count);
        }
        return count;
    }

    /**
     * Sets the cursor to a node with a specific file path. Recursively traverses through the tree following the path
     * @param path An array of strings that contains the path in order from root to the desired node 
     * @param targetIndex The index in path to compare cursor's children's name to
     */
    public void followPath(String[] path, int targetIndex){
        if(targetIndex == path.length){
            return ;
        }

        String target = path[targetIndex];

        if(cursor.getLeft() != null && cursor.getLeft().getName().equals(target)){
            cursor = cursor.getLeft();
        }
        else if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(target)){
            cursor = cursor.getMiddle();
        }
        else if(cursor.getRight() != null && cursor.getRight().getName().equals(target)){
            cursor = cursor.getRight();
        }
        else{
            System.out.println("ERROR: Invalid file path.");
            return;
        }

        followPath(path, targetIndex + 1);
    }

    /**
     * Moves the src node to a child reference of the dest node. All of src's children will follow
     * @param src The node to move to a child reference of dest
     * @param dest The node that src will become a child of
     * @throws NotADirectoryException If dest's isFile variable is true, throw an error
     * @throws FullDirectoryException If all of dest's children reference is full, throw an error
     */
    public void srcToDest(DirectoryNode src, DirectoryNode dest) throws NotADirectoryException, FullDirectoryException{
        DirectoryNode srcParent = src.getParent();
        if(srcParent.getLeft() == src){
            srcParent.setLeft(null);
        }
        else if(src.getMiddle() == src){
            srcParent.setMiddle(null);
        }
        else{
            srcParent.setRight(null);
        }

        src.setParent(dest);
        dest.addChildNode(src);
    }
}
