# Java File & I/O Exercises – Level 1

Educational Java project to practice file system operations, recursive directory traversal, I/O streams and object serialization.

## Objective

- Master basic file/directory handling with `java.io.File`
- Implement recursive directory listing (tree structure)
- Read/write text files
- Serialize and deserialize Java objects
- Build reusable utilities for file system tasks

## Exercises Covered

1. **Alphabetical directory listing**  
   List contents of a directory in alphabetical order

2. **Recursive directory tree**  
   Print full directory tree (recursive), alphabetically ordered per level, with type (D/F) and last modified date

3. **Save tree to TXT file**  
   Modify previous exercise to write output to a text file instead of console

4. **Read TXT file content**  
   Read and display any TXT file to console

5. **Object serialization**  
   Serialize a Java object to `.ser` file  
   Deserialize and reconstruct the object

## Project Structure

```text
javaUtils/
├── pom.xml
└── src/
├── main/java/
│   └── Main.java
├── test/java/
│   └── MainTest.java 

```
## How to Run

```bash
# Build and verify the project
mvn clean verify

# Run the application with the example directory
mvn exec:java -Dexec.mainClass="org.javaUtils.App" -Dexec.args="docs/example_directory"
```