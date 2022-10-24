public class NotADirectoryException extends Exception{
    public NotADirectoryException(String message){
        super(message);
    }

    public NotADirectoryException(){
        super();
    }
}
