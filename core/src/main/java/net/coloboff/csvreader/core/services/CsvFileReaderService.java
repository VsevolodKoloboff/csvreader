package net.coloboff.csvreader.core.services;

import net.coloboff.csvreader.core.dto.AirportDTO;
import net.coloboff.csvreader.core.dto.IndexDTO;
import net.coloboff.csvreader.core.utils.QuoteUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* reads csv file
*/

@Service
public class CsvFileReaderService {

    private static final String  COMMA_DELIMITER = ",";

    @Value("${csv.loaction:#{null}}")
    private String csvFileLocation;

    private File csvFile;


    @PostConstruct
    private void init() throws IOException {
        if (!StringUtils.isEmpty(csvFileLocation))
            loadFromDisk();
        if (csvFile == null)
            loadFromClasspath();

    }

    /**
    * reads all data from csv file
    *
    * @return;
    * @throws IOException
    */
    public List<AirportDTO> readAll() throws IOException {
        List<AirportDTO> result = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile)){
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while (bufferedReader.ready())
                    result.add(new AirportDTO((bufferedReader.readLine().split(COMMA_DELIMITER))));
            }
        }
        return result;
    }

    /**
    *   Loads indexes for the selected column.
    *@param columnIndexNum - номер колонки.
    *@return
    *@throws IOException
    */
    public List <IndexDTO> loadIndexes (int columnIndexNum) throws IOException {
        List<IndexDTO> result = new ArrayList<>();
        try (RandomAccessFile randomAccessFile =new RandomAccessFile(csvFile, "r")) {
            while (true) {
                Long index = randomAccessFile.getFilePointer();
                String csvLine = randomAccessFile.readLine();
                if (csvLine == null)
                    break;
                randomAccessFile.seek(randomAccessFile.getFilePointer());
                try {
                    String filterVal = QuoteUtils.removeQuoteIfExist(csvLine.split(COMMA_DELIMITER) [columnIndexNum-1]);
                    IndexDTO indexDTO = new IndexDTO(filterVal, index);
                    result.add(indexDTO);
                } catch (ArrayIndexOutOfBoundsException exe) {
                    throw new RuntimeException(String.format("Column number for indexing is not set correctly: %s",
                            columnIndexNum), exe);

                }
            }
        }
        return result;
    }

    /**
    *loads index data
    *@param offsets
    *@return
    *@throws IOException
    */

    public List <AirportDTO> readByIndex (List <Long> offsets) throws IOException {
        List<AirportDTO>   result =new ArrayList<>();
        try (RandomAccessFile randomAccessFile =new RandomAccessFile(csvFile,"r")){
            for (Long offset : offsets) {
                randomAccessFile.seek(offset);
                result.add(new AirportDTO(randomAccessFile.readLine().split(COMMA_DELIMITER)));
            }
        }
        return result;
    }



    private void loadFromDisk () {
        File file =new File(csvFileLocation);
        if (file.exists())
            csvFile = file;
    }

    private void loadFromClasspath() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("airports.dat");
        if (!classPathResource.exists())
            throw new RuntimeException("Data file could not be found");

        File temp = File.createTempFile("airports", "dat");

//    reads temp file through Random access
        try (InputStream in = new BufferedInputStream(classPathResource.getInputStream());
             OutputStream out = new BufferedOutputStream(
                     new FileOutputStream(temp))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead=in.read(buffer)) > 0) {
                out.write(buffer, 0 , lengthRead);
                out.flush();
            }
        }
        csvFile = temp;
    }
}
