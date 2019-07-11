package net.coloboff.csvreader.core.services;

import net.coloboff.csvreader.core.CsvReader;
import net.coloboff.csvreader.core.dto.AirportDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class CsvReaderImpl implements CsvReader {

    private final CsvFileReaderService csvFileReaderService;
    private final IndexService indexService;

    public CsvReaderImpl(CsvFileReaderService csvFileReaderService, IndexService indexService) {
        this.csvFileReaderService = csvFileReaderService;
        this.indexService = indexService;
    }

    @Override
    public Collection<AirportDTO> getAll() throws IOException {
        return csvFileReaderService.readAll();
    }

    @Override
    public Collection<AirportDTO> filter(String fieldValue) throws IOException {
        return csvFileReaderService.readByIndex(indexService.getIndexes(fieldValue));
    }

    @Override
    public Collection<String> getAllFilters() {
        return indexService.getIndexesKeys();
    }
}
