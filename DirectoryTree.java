public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    public DirectoryTree(){
        root = new DirectoryNode("root", false, null);
        cursor = root;
    }

    public DirectoryNode getCursor(){
        return cursor;
    }

    public void resetCursor(){
        cursor = root;
    }

    public void setCursor(DirectoryNode node){
        cursor = node;
    }

    public void changeDirectory(String name) throws NotADirectoryException{
        if(!changeDirectoryHelper(name, root)){
            System.out.println("ERROR: No such directory named '" + name + "'." );
        }
    }

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

    public String presentWorkingdirectory(String target){
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

    public void printDirectoryTree(DirectoryNode root){
        printDirectoryTreeHelper(root, 0);
    }

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

    public void makeDirectory(String name) throws IllegalArgumentException, NotADirectoryException, FullDirectoryException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("Directory name should not contain spaces or forward slashes.");
        }
        DirectoryNode directory = new DirectoryNode(name, false, cursor);
        cursor.addChildNode(directory);
    }

    public void makeFile(String name) throws IllegalArgumentException, NotADirectoryException, FullDirectoryException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("File name should not contain spaces or forward slashes.");
        }

        DirectoryNode file = new DirectoryNode(name, true, cursor);
        cursor.addChildNode(file);
    }

    public int findFile(String name){
        return findFileHelper(root, "", name, 0);
    }

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
