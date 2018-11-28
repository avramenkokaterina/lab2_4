package avramenko.filesWorks;

import avramenko.model.Model;

import java.io.IOException;

public interface WorkerWithFiles {

    boolean readFromFile(Model model) throws IOException;
    boolean writeToFile(Model model) throws IOException;
}
