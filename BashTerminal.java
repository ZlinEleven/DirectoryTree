/** Zhenbin Lin, 114866923, Recitation 04 */

import java.util.Scanner;

/**
 * This class contains the main method which is a menu driven application
 * This program prompts the user for simulation parameters to run the simulation on.
 * The user can also input "exit" to terminate the program.
 */
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
                System.out.println(tree.presentWorkingDirectory(tree.getCursor().getName()));
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

            else if(!command.contains("/") && command.contains("cd ") && !command.equals("cd ..")){
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

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**EXTRA CREDIT */
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if(command.contains("find ")){
                int occurrence = tree.findFile(command.split(" ")[1]);
                if(occurrence == 0){
                    System.out.println("ERROR: No such file exists.");
                }
            }

            else if(command.equals("cd ..")){
                if(tree.getCursor().getParent() == null){
                    System.out.println("ERROR: Already at root directory.");
                }
                else{
                    tree.setCursor(tree.getCursor().getParent());
                }
            }

            else if(command.contains("cd")){
                tree.followPath(command.split(" ")[1].split("/"), 0);
            }

            else if(command.contains("mv")){
                tree.followPath(command.split(" ")[1].split("/"), 1);
                DirectoryNode source = tree.getCursor();
                tree.resetCursor();
                tree.followPath(command.split(" ")[2].split("/"), 1);
                DirectoryNode dest = tree.getCursor();
                try{
                    tree.srcToDest(source, dest);
                }
                catch(NotADirectoryException e){
                    System.out.println("Destination is not a directory.");
                }
                catch(FullDirectoryException e){
                    System.out.println("Destination is full.");
                }

                tree.resetCursor();
            }

            System.out.print(commandLine);

            command = scan.nextLine();
        }
        System.out.println("Program terminating normally");
        scan.close();

    }
}
