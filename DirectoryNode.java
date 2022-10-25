public class DirectoryNode{
    private String name;
    private DirectoryNode left;
    private DirectoryNode middle;
    private DirectoryNode right;
    private DirectoryNode parent;
    private boolean isFile;

    public DirectoryNode(String name, boolean isFile, DirectoryNode parent){
        this.name = name;
        left = null;
        middle = null;
        right = null;
        this.isFile = isFile;
        this.parent = parent;
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

    public void setLeft(DirectoryNode node){
        left = node;
    }

    public DirectoryNode getMiddle(){
        return middle;
    }

    public void setMiddle(DirectoryNode node){
        middle = node;
    }

    public DirectoryNode getRight(){
        return right;
    }

    public void setRight(DirectoryNode node){
        right = node;
    }

    public DirectoryNode getParent(){
        return parent;
    }

    public void setParent(DirectoryNode node){
        parent = node;
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
                throw new FullDirectoryException("ERROR: Present directory is full.");
            }
        }
    }
}