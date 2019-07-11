package net.coloboff.csvreader.web.controllers;

import net.coloboff.csvreader.core.CsvReader;
import net.coloboff.csvreader.core.dto.AirportDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/airport")
public class AirportResource {

    private final CsvReader csvReader;

    public AirportResource(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    /**
     * Returns a list of all airports.
     *
     * @return
     * @throws IOException
     */

    @GetMapping("/all")
    public Collection<AirportDTO> getAll() throws IOException {
        return csvReader.getAll();
    }
    /**
     *Returns a list of filtered airports by the column value specified in the properties.
     *
     *@param value
     *@return
     *@throws IOException
     */
    @GetMapping("/filter")
    public Collection<AirportDTO> filter(@RequestParam(name = "value") final  String value) throws IOException {
        return csvReader.filter(value);
    }

    /**
    * Returns all possible filters.
    */
    @GetMapping("/filters")
    public Collection<String> getAllFilters() {
        return csvReader.getAllFilters();
    }
}
