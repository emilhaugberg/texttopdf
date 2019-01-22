/*
 * The PDFState class enables us to keep track of the state of the parsing.
 * For example, using this class, we know if some text should be written using bold,
 * italic or regular typography. Other notable data it keeps track of, is that of the
 * number of indents, the font size and if we should fill the text.
 */

package com.emilvanveen.texttopdf;

public class PDFState {

    private int indent = 0;
    private int maxIndent = 8;
    private boolean fill = false;
    private Enums.Size size = Enums.Size.NORMAL;
    private Enums.Font font = Enums.Font.REGULAR;

    /**
     * Sets the indent amount, which is capped on 8 indents to the right and 0
     * to the left.
     *
     * @param indent New number of indents.
     */
    public void setIndent(int indent) {
        /* Cap the indent such that the text does not exceed the PDF. */
        int newIndents = indent >= this.maxIndent
                ? this.maxIndent
                : indent <= 0 ? 0 : indent;

        this.indent = newIndents;
    }

    /**
     * Returns the number of indents.
     *
     * @return int
     */
    public int getIndent() {
        return indent;
    }

    /**
     *
     * @param fill Boolean indicating whether we should fill the text.
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     *
     * @return boolean
     */
    public boolean getFill() {
        return this.fill;
    }

    /**
     *
     * @param size Font size.
     */
    public void setSize(Enums.Size size) {
        this.size = size;
    }

    /**
     *
     * @return Font size.
     */
    public Enums.Size getSize() {
        return size;
    }

    /**
     * Sets the font type.
     *
     * @param font Font type.
     */
    public void setFont(Enums.Font font) {
        this.font = font;
    }

    /**
     * Get the font type.
     *
     * @return Font type.
     */
    public Enums.Font getFont() {
        return font;
    }


    /**
     * Updates the state given a command.
     *
     * @param command String representing a command.
     */
    public void updateState(String command) {
       if(command.toLowerCase().contains(".indent")) {
           setIndent(this.indent + Integer.parseInt(command.split(" ")[1]));
       } else if (command.equals(".fill")) {
           setFill(true);
       } else if (command.equals(".nofill")) {
           setFill(false);
       } else if (command.equals(".regular")) {
           setFont(Enums.Font.REGULAR);
       } else if (command.equals(".bold")) {
           setFont(Enums.Font.BOLD);
       } else if (command.equals(".italics")) {
           setFont(Enums.Font.ITALICS);
       } else if (command.equals(".large")) {
           setSize(Enums.Size.LARGE);
       } else if (command.equals(".normal")) {
           setSize(Enums.Size.NORMAL);
       }
    }
}
