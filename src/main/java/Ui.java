import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Ui {

    protected Scanner scan = new Scanner(System.in);
    protected TaskList tL = new TaskList();

    public void greeting() {
        String logo = "     ____        _        \n"
                    + "    |  _ \\ _   _| | _____ \n"
                    + "    | | | | | | | |/ / _ \\\n"
                    + "    | |_| | |_| |   <  __/\n"
                    + "    |____/ \\__,_|_|\\_\\___|\n";

        // Prints out greeting of the chat bot.
        printLine();
        printIndent();
        System.out.println("Hello! My name is \n" + logo + "\n" + "    What can I do for you? \n");
        printIndent();
        System.out.println("I can only do these functions for now: \n \n" + "  Todo \n" + "  Event \n" + "  Deadline \n" + "  Delete \n" + "  Done \n" + "  List \n");
        printIndent();
        System.out.println("Ill be adding in more features soon! Please be patient! :)");
        printIndent();
        System.out.println("I can only accept dates in this following format: \n" +
                "    dd/MM/yyyy hhmm. Please adhere to it! Thank you!" );
        printLine();
    }

    public void nextCommand() {
        while (scan.hasNext()) {
            try {
                String text = scan.nextLine().trim();
                if (text.equals("bye")) {
                    printBye();
                    break;

                } else if (text.equals("list")) {
                    printList();
                } else if (text.equals("delete all")) {
                    deleteAll();
                } else if (text.indexOf(" ") > -1) {

                    String[] splittedText = text.split(" ");

                    if (splittedText[0].equals("done")) {
                        int num = text.indexOf(" ");
                        int taskNumber = Integer.parseInt(text.substring(num + 1, num + 2));
                        if (taskNumber > 0 && taskNumber <= TaskList.listOfTasks.size()) {
                            printDone(taskNumber);
                            //tasking[num] = arrayList.get((taskNumber - 1));
                        } else {
                            throw new DukeException("☹ OOPS!!! There is no such task number in your list of tasks!! " +
                                    "Please enter a valid number!");
                        }
                    } else if (splittedText[0].equals("delete")) {
                        int num = text.indexOf(" ");
                        int taskNumber = Integer.parseInt(text.substring(num + 1, num + 2));
                        Task.printRemove();
                        printDelete(taskNumber);
                        TaskList.listOfTasks.remove(taskNumber - 1);
                        Storage.writeToFile(Storage.file, "");
                        for (Task task : TaskList.listOfTasks) {
                            Storage.addToFile(Storage.file, task.toString());
                        }
                        Task.printNumOfTasks();
                    } else {
                        if (splittedText[0].equals("todo")) {
                            Task.printGI();
                            printIndent();
                            int num = text.indexOf(" ");
                            Task task = new Todo(text.substring(num + 1));
                            System.out.println("  " + task.toString());
                            TaskList.listOfTasks.add(task);
                            Storage.addToFile(Storage.file, task.toString());
                            Task.printNumOfTasks();


                        } else if (splittedText[0].equals("deadline") &&
                                text.contains("/") && text.contains("by")) { // what if there is no deadline
                            int num = text.indexOf("/");
                            int num1 = text.indexOf(" ");
                            Task task = new Deadline(text.substring(num1, num - 1),
                                    text.substring(num + 4));
                            String taskers = task.toString();
                            if (!taskers.equals("Invalid date format!")) {
                                Task.printGI();
                                printIndent();
                                System.out.println("  " + taskers);
                                TaskList.listOfTasks.add(task);
                                Storage.addToFile(Storage.file, taskers);
                                Task.printNumOfTasks();
                            } else {
                                printIndent();
                                throw new DukeException(taskers);
                            }


                        } else if (splittedText[0].equals("event") &&
                                text.contains("/") && text.contains("at")) { // what if there is no date
                            int num = text.indexOf("/");
                            int num1 = text.indexOf(" ");
                            Task task = new Event(text.substring(num1, num - 1),
                                    text.substring(num + 4));
                            String taskers = task.toString();
                            if (!taskers.equals("Invalid date format!")) {

                                Task.printGI();
                                printIndent();
                                System.out.println("  " + taskers);
                                TaskList.listOfTasks.add(task);
                                Storage.addToFile(Storage.file, taskers);
                                Task.printNumOfTasks();
                            } else {
                                printIndent();
                                throw new DukeException(taskers);
                            }
                        } else {
                            printLine();
                            printIndent();
                            throw new DukeException("☹ OOPSY DAISY!!! Please follow the correct format! :<");
                        }
                    }
                } else {
                    printLine();
                    printIndent();
                    switch (text) {
                        case "todo":
                            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty. " +
                                    "It must be in proper format (i.e. todo clean table).");
                        case "deadline":
                            throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty. " +
                                    "It must be in proper format (i.e. deadline return book /by 23 Aug).");
                        case "event":
                            throw new DukeException("☹ OOPS!!! The description of a event cannot be empty. " +
                                    "It must be in proper format (i.e. event Don's birthday /at 15 Jan 3pm).");
                        default:
                            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }
            } catch (DukeException | IOException e) {
                System.out.println(e);
                printLine();
            }
        }
    }

    /**
     * Prints indentation.
     * Helps to order the output, making it much neater.
     */
    public static void printIndent() {
        System.out.print("    ");
    }

    // Prints the line. For better organisation.

    /**
     * Prints line.
     * Helps to order the output and makes it
     * much neater.
     */
    public static void printLine() {
        printIndent();
        System.out.println("___________________________________________________________________");
    }

    /**
     * Ends the chat bot.
     */
    private static void printBye() {
        printLine();
        printIndent();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Marks a task as done once the user has finished it.
     *
     * @param i To indicate which task number is done.
     */
    private static void printDone(int i) {
        TaskList.listOfTasks.get(i-1).markAsDone();
        printLine();
        printIndent();
        System.out.println("Nice! I've marked this task as done:");
        printIndent();
        System.out.println(TaskList.listOfTasks.get(i-1).toString());
        printLine();
    }

    /**
     * To remove a task if it is not needed anymore.
     *
     * @param i Indicates the task number that is done.
     */
    private static void printDelete(int i) {
        printIndent();
        System.out.println(TaskList.listOfTasks.get(i-1).toString());
        printLine();
    }

    /**
     * Prints the list of tasks that has been added by the user.
     *
     * @throws FileNotFoundException if there is no such file that contains the tasks.
     */
    private static void printList() throws FileNotFoundException {
        printLine();
        printIndent();
        if (TaskList.listOfTasks.isEmpty()) {
            System.out.println("There is no tasks in your list currently!!!");
        } else {
            System.out.println("Here are the tasks in your list:");
            File temp = new File(Storage.file);
            Scanner s = new Scanner(temp);
            int numbering = 1;
            while (s.hasNext()) {
                printIndent();
                System.out.println(numbering + ". " + s.nextLine());
                numbering++;
            }
        }
        printLine();
    }

    /**
     * Deletes everything off the task list.
     *
     * @throws IOException If the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason.
     */
    private static void deleteAll() throws IOException{
        Storage.writeToFile(Storage.file, "");
        TaskList.listOfTasks.clear();
        printLine();
        printIndent();
        System.out.println("Everything in your list has been removed! Add more tasks to get started again!!!");
        printLine();
    }

    /**
     * Gets the number of task inside the file.
     *
     * @return Number of tasks.
     * @throws IOException If the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason.
     */
    public static int getNumOfTasks() throws IOException {
        return Storage.countLines(Storage.file);
    }

    public void showLoadingError() {
        printLine();
        printIndent();
        System.out.println("Nothing in file!");
    }
}