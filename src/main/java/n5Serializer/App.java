package n5Serializer;

import n4FileReader.TextFileReader;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String filePath = "data" + File.separator + "person.ser";

        Person person = new Person("Camila", "Villarroel", 32);
        System.out.println("Original:  " + person);

        serialize(person, filePath);
        deserialize(filePath);
    }

    private static void serialize(Person person, String filePath) {
        try {
            new Serializer().serialize(person, filePath);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid argument: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("[ERROR] I/O error during serialization: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void deserialize(String filePath) {
        try {
            Person recovered = (Person) new Deserializer().deserialize(filePath);
            System.out.println("Recovered: " + recovered);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid argument: " + e.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] Class not found during deserialization: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("[ERROR] I/O error during deserialization: " + e.getMessage());
            System.exit(1);
        }
    }
}
