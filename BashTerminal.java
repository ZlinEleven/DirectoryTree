import java.util.Scanner;

public class BashTerminal {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        DirectoryTree tree = new DirectoryTree();

        String commandLine = "[114866923@data]: $ ";

        System.out.println("Starting bash terminal.");
        System.out.print(commandLine);

        String command = scan.nextLine();

        while(!command.equals("exit")){
            if(command.equals("pwd")){
                System.out.println(tree.presentWorkingdirectory());
            }

            else if(command.contains("mkdir")){
                try{
                    tree.makeDirectory(command.split(" ")[1]);
                }
                catch(NotADirectoryException e){
                    System.out.println(e.getMessage());
                }
                catch(FullDirectoryException e){
                    System.out.println(e.getMessage());
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(command.equals("ls")){
                System.out.println(tree.listDirectory());
            }

            else if(command.equals("cd /")){
                tree.resetCursor();
            }

            else if(command.contains("cd ")){
                try{
                    tree.changeDirectory(command.split(" ")[1]);
                }
                catch(NotADirectoryException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(command.contains("touch")){
                try{
                    tree.makeFile(command.split(" ")[1]);
                }
                catch(NotADirectoryException e){
                    System.out.println(e.getMessage());
                }
                catch(FullDirectoryException e){
                    System.out.println(e.getMessage());
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(command.equals("ls -R")){
                System.out.println();
                tree.printDirectoryTree(tree.getCursor());
                System.out.println();
            }

            System.out.print(commandLine);

            command = scan.nextLine();
        }
        System.out.println("Program terminating normally");

    }
}
