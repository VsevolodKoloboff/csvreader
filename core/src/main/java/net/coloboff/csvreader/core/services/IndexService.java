package net.coloboff.csvreader.core.services;

import net.coloboff.csvreader.core.dto.IndexDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class IndexService {

    private final Map<String, List<Long>> indexes = new HashMap<>();
    private final CsvFileReaderService csvFileReaderService;

    @Value("4")
    private Integer indexColumnNumber;

    public IndexService(CsvFileReaderService csvFileReaderService) {
        this.csvFileReaderService = csvFileReaderService;
    }

    @PostConstruct
    private void init() throws IOException {
        for (IndexDTO indexDTO : csvFileReaderService.loadIndexes(indexColumnNumber)){
            indexes.putIfAbsent(indexDTO.getFieldValue(), new ArrayList<>());
            indexes.get(indexDTO.getFieldValue()).add(indexDTO.getOffset());
        }
    }

    public List <Long> getIndexes (final String columnValue) {
        if (!indexes.containsKey(columnValue))
            throw new RuntimeException(String.format("The searched value was not indexed: %s", columnValue));
        return indexes.get(columnValue);
    }

    public Collection<String> getIndexesKeys() {
        return indexes.keySet();
    }
}
