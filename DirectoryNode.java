public class DirectoryNode{
    private String name;
    private DirectoryNode left;
    private DirectoryNode middle;
    private DirectoryNode right;
    private boolean isFile;

    public DirectoryNode(String name, boolean isFile){
        this.name = name;
        left = null;
        middle = null;
        right = null;
        this.isFile = isFile;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public DirectoryNode getLeft(){
        return left;
    }

    public DirectoryNode getMiddle(){
        return middle;
    }

    public DirectoryNode getRight(){
        return right;
    }

    public boolean getIsFile(){
        return isFile;
    }

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
                throw new FullDirectoryException("Full directory.");
            }
        }
    }
}