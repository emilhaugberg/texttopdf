* Implementation of the PDF generator

** Java Classes

There are three main classes:
FileToPDF: The entry point. This reads a file and calls the PDF generator, after which it writes the resulting PDF to a file.
ParseToPDF: Implements the parsing logic and produces the PDF.
PDFState: Used to keep track of the state of the parsing.

** Some assumptions I've made:

- I was not sure as to how the .paragraph command was supposed to work, as there is no command to end the paragraph.
  The same thing goes for other commands, such as indent and fill, as these also seem to break the text up into blocks.
  I have therefore chosen to start with a new block of text when these commands are used.

- For indenting, I've capped the number of indents in such a way that there is no possibility for them to be placed
  outside of the PDF.

- When for example making a single word bold or italic, the code does not add spaces before or after the word. These spaces
  are therefore required to be present in the text file itself.

** Things I would have done differently

- The logic of the parser could be more solid in my opinion. I currently do this by comparing string, and
  updating the state based on these strings. If I had more time, I would have created a different class or
  type to deal with these kind of comparisons.

* Building and running the code

- I've used SDK version 1.8 in combination with Language level 8
- I've included some test files in the tests directory. When calling FileToPDF.main() function, these
  these tests should run and output pdf files in the results directory.