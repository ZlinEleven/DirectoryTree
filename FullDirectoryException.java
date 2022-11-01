/** Zhenbin Lin, 114866923, Recitation 04 */


/**
 * This is a custom exception intended for the case where a node is attempted to be added to a DirectoryNode already with three children
 */
public class FullDirectoryException extends Exception{
    public FullDirectoryException(String message){
        super(message);
    }

    public FullDirectoryException(){
        super();
    }
}
