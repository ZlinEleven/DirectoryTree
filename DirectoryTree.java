import java.util.Scanner;

public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    public DirectoryTree(){
        root = new DirectoryNode("root", false);
        cursor = root;
    }

    public DirectoryNode getCursor(){
        return cursor;
    }

    public void resetCursor(){
        cursor = root;
    }

    public void changeDirectory(String name) throws NotADirectoryException{
        if(!changeDirectoryHelper(name, root)){
            System.out.print("The directory name entered couldn't be found. Please try again: ");
            Scanner scan = new Scanner(System.in);

            String newName = scan.nextLine();
            changeDirectory(newName);
        }
    }

    public boolean changeDirectoryHelper(String name, DirectoryNode root) throws NotADirectoryException{
        if(root == null){
            return false;
        }
        if(root.getIsFile()){
            if(root.getName().equals(name)){
                throw new NotADirectoryException("File name entered is not a directory. Please try again.");
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

    public String presentWorkingdirectory(){
        DirectoryNode pointer = root;
        String ans = pointer.getName();

        while(pointer != cursor){
            if(presentWorkingDirectoryHelper(pointer.getLeft())){
                pointer = pointer.getLeft();
            }
            else if(presentWorkingDirectoryHelper(pointer.getMiddle())){
                pointer = pointer.getMiddle();
            }
            else{
                pointer = pointer.getRight();
            }
            ans += "/" + pointer.getName();
        }
        return ans;
    }

    public boolean presentWorkingDirectoryHelper(DirectoryNode root){
        if(root == null){
            return false;
        }
        if(root == cursor){
            return true;
        }
        
        return presentWorkingDirectoryHelper(root.getLeft()) || presentWorkingDirectoryHelper(root.getMiddle()) || presentWorkingDirectoryHelper(root.getRight());

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
        DirectoryNode directory = new DirectoryNode(name, false);
        cursor.addChildNode(directory);
    }

    public void makeFile(String name) throws IllegalArgumentException, NotADirectoryException, FullDirectoryException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("File name should not contain spaces or forward slashes.");
        }

        DirectoryNode file = new DirectoryNode(name, true);
        cursor.addChildNode(file);
    }
}
