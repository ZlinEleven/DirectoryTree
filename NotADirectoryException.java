/** Zhenbin Lin, 114866923, Recitation 04 */


/**
 * This is a custom exception intended for the case where a child is attempted to be added DirectoryNode with a true isFile variable
 */
public class NotADirectoryException extends Exception{
    public NotADirectoryException(String message){
        super(message);
    }

    public NotADirectoryException(){
        super();
    }
}
