package me.heroostech.floodgate.forms;

import java.io.Serializable;
import java.util.UUID;

/**
 * Base class of all Forms. While it can be used it doesn't contain every data you could get when
 * using the specific class of the form type.
 */
public interface Form extends Serializable {
    /**
     * Returns the title of the Form.
     */
    String title();

    /**
     * Returns a type of form
     */
    String type();

    /**
     * Returns a form's player
     */
    UUID player();
}