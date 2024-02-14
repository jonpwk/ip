package command;

import cro.Cro;
import cro.CroException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Representation of a Parser to handle user input in the program.
 */
public class Parser {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy M d HH mm");

    /**
     * Constructor for a Parser object.
     */
    public Parser() {
    }

    /**
     * Returns a List that specifies a Deadline task. This function parses the deadline in user
     * input from [yyyy MM dd hh mm] to a common format for saving and reading within the
     * program.
     * @param splitStr A list of strings from the user's inputs that describes a Deadline.
     * @return A list of strings that describes a Deadline with standardised time format.
     * @throws CroException If any specifications of the deadline is missing.
     */
    public List<String> convertDateDeadline(List<String> splitStr) throws CroException {
        int byIndex = splitStr.indexOf("/by");
        if (byIndex < 0) {
            throw new CroException("deadline not found, please include with '/by' as an indicator.");
        }
        List<String> dateSplitStr = splitStr.subList(byIndex + 1, splitStr.size());
        LocalDateTime time = LocalDateTime.parse(String.join(" ", dateSplitStr), inputFormat);
        splitStr.subList(byIndex + 1, splitStr.size()).clear();
        splitStr.add(time.toString());
        return splitStr;
    }
    /**
     * Returns a List that specifies an Event task. This function parses the deadline in user
     * input from [yyyy MM dd hh mm] to a common format for saving and reading within the
     * program.
     * @param splitStr A list of strings from the user's inputs that describes an Event.
     * @return A list of strings that describes a Deadline with standardised time format.
     * @throws CroException If any specifications of the event is missing.
     */
    public List<String> convertDateEvent(List<String> splitStr) throws CroException {
        int fromIndex = splitStr.indexOf("/from");
        int toIndex = splitStr.indexOf("/to");

        if (fromIndex < 0 || toIndex < 0) {
            throw new CroException("event timings not found, please use /from and /to to indicate.");
        }

        List<String> fromSplitStr = splitStr.subList(fromIndex + 1, toIndex);
        List<String> toSplitStr = splitStr.subList(toIndex + 1, splitStr.size());
        LocalDateTime from = LocalDateTime.parse(String.join(" ", fromSplitStr), inputFormat);
        LocalDateTime to = LocalDateTime.parse(String.join(" ", toSplitStr), inputFormat);

        splitStr.subList(toIndex + 1, splitStr.size()).clear();
        splitStr.add(to.toString());
        splitStr.subList(fromIndex + 1, toIndex).clear();
        splitStr.add(fromIndex + 1, from.toString());

        return splitStr;
    }

    /**
     * Returns a boolean to indicate if the program is still accepting inputs.
     * @param cro The chatbot that the Parser is parsing from.
     * @return True if continuing to accept input, else False.
     */
    public boolean handleInput(Cro cro) {
        String inText = scanner.nextLine();
        List<String> splitStr = new ArrayList<>(Arrays.asList(inText.trim().split("\\s+")));
        String command = splitStr.remove(0);
        try {
            switch (command) {
            case "bye":
                System.out.println("-----------------------------------");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("-----------------------------------");
                return false;
            case "list":
                cro.taskList.displayTasks();
                break;
            case "mark":
                cro.taskList.markTaskAsDone(splitStr);
                break;
            case "unmark":
                cro.taskList.markTaskAsUndone(splitStr);
                break;
            case "todo": {
                List<String> res = new ArrayList<>(Arrays.asList("T", "0"));
                res.addAll(splitStr);
                cro.taskList.addToDo(res);
                break;
            }
            case "deadline": {
                List<String> res = new ArrayList<>(Arrays.asList("D", "0"));
                splitStr = convertDateDeadline(splitStr);
                res.addAll(splitStr);
                cro.taskList.addDeadline(res);
                break;
            }
            case "event": {
                List<String> res = new ArrayList<>(Arrays.asList("E", "0"));
                splitStr = convertDateEvent(splitStr);
                res.addAll(splitStr);
                cro.taskList.addEvent(res);
                break;
            }
            case "delete":
                cro.taskList.deleteEvent(splitStr);
                break;
            default:
                throw new CroException("Unknown command. Please try again.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }
}
