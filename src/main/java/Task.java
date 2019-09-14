import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    protected String description;
    protected boolean isDone;
    protected boolean isCorrectFormat;

    /**
     * Constructor for Task.
     *
     * @param description Takes in a string that is
     *     either Todo, Event or Deadline.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.isCorrectFormat = false;
    }

    /**
     * Method to get whether that date is in correct format.
     *
     * @return Returns the boolean isCorrectFormat for the date.
     */
    public boolean getIsCorrectFormat() {
        return isCorrectFormat;
    }

    /**
     * Returns tick or cross symbol.
     *
     * @return A tick or cross to symbolize whether the task has been done.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Method to mark the task to done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Method to get the String for whether
     * the task is done or not.
     *
     * @return Returns the tick or cross in brackets.
     */
    public String toString() {
        return "[" + getStatusIcon() + "]";
    }


}
