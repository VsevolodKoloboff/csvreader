package net.coloboff.csvreader.core;

import net.coloboff.csvreader.core.dto.AirportDTO;

import java.io.IOException;
import java.util.Collection;

public interface CsvReader {

    /**
    * return all data
    * @return
    */
    Collection <AirportDTO> getAll() throws IOException;

    /**
    * return data, filter by field
    * @return
    */
    Collection<AirportDTO> filter (String fieldValue) throws IOException;

    /**
    * return the possible values
    * @return
    */
    Collection <String> getAllFilters();
}
