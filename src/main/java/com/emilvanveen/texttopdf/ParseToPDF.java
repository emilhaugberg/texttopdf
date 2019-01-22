/*
 * The ParseToPDF class converts a read file into a PDF. It does this by keeping a state using
 * the PDFState class, using the state to apply the commands correctly.
 */

package com.emilvanveen.texttopdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ParseToPDF {
    private PDFState state;
    private List<String> lines;

    private int fontSizeLarge = 25;
    private int fontSizeSmall = 14;
    private int singleIndentSize = 20;

    /**
     *
     * @param lines A list consisting of each line in the file.
     * @throws IOException
     */
    ParseToPDF(List<String> lines) throws IOException {
        this.state = new PDFState();
        this.lines = lines;
    }

    /**
     * Converts a list of strings to a PDF.
     * For each line, we check if it represents a command or text, updating the state
     * appropriately. Some commands imply a so-called break point, which are points
     * that break the text into pieces, ultimately giving us the margin between blocks
     * of text.
     *
     * @param dest Destination path of the PDF.
     * @throws FileNotFoundException Thrown when given a non-existing file.
     */
    protected void toPDF(String dest) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        Paragraph p = new Paragraph();

        for (String s : this.lines) {
            /* If this is a "breakpoint" command, we can write this paragraph
             * to the PDF and start with a new one. */
            if (this.isBreakPoint(s)) {
                document.add(p);
                p = new Paragraph();
            }

            if (isCommand(s)) {
                this.state.updateState(s);
            } else {
                this.updateParagraph(p, s);
            }
        }

        document.add(p);
        document.close();
    }

    /**
     * Updates a given paragraph.
     *
     * @param p A Paragraph object.
     * @param s A string containing text.
     */
    private void updateParagraph(Paragraph p, String s) {
        /* Set the font size and the style based on the current state. */
        Text t = new Text(s);
        this.setTextFontSize(t);
        this.setTextFontStyle(t);

        /* Set the paragraph indent and alignment based on the current state. */
        p.add(t);
        this.setParagraphIndent(p);
        this.setParagraphAlignment(p);
    }

    /**
     *  Says if we need to break up the text.
     *
     * @param s A string representing a command or some text.
     * @return Boolean indicating if we need to break up the text.
     */
    private boolean isBreakPoint(String s) {
        return  s.equals(".paragraph")  ||
                s.contains(".indent")   ||
                s.equals(".fill")       ||
                s.equals(".large")      ||
                s.equals(".normal");
    }

    /**
     *  Says whether a given string is a command or not.
     *
     * @param s A string representing a command or some text.
     * @return If a string represents a command.
     */
    private boolean isCommand(String s) {
        return s.charAt(0) == '.';
    }

    /**
     * Sets the font size of some text.
     *
     * @param t A text object.
     */
    private void setTextFontSize(Text t) {
        t.setFontSize(this.state.getSize() == Enums.Size.LARGE ? this.fontSizeLarge : this.fontSizeSmall);
    }

    /**
     * Sets the font style of some text.
     *
     * @param t A text object.
     */
    private void setTextFontStyle(Text t) {
        if (this.state.getFont() == Enums.Font.BOLD) {
            t.setBold();
        } else if (this.state.getFont() == Enums.Font.ITALICS) {
            t.setItalic();
        }
    }

    /**
     * Sets the indenting for a paragraph.
     *
     * @param p A paragraph object.
     */
    private void setParagraphIndent(Paragraph p) {

        p.setMarginLeft((float)this.singleIndentSize * this.state.getIndent());
    }

    /**
     * Sets the alignment for a paragraph.
     *
     * @param p A paragraph object.
     */
    private void setParagraphAlignment(Paragraph p) {
        p.setTextAlignment(this.state.getFill() ? TextAlignment.JUSTIFIED : TextAlignment.LEFT);
    }
}
