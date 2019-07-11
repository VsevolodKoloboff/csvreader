package net.coloboff.csvreader.core.dto;

import net.coloboff.csvreader.core.utils.QuoteUtils;

import java.util.ArrayList;
import java.util.List;

public class AirportDTO {
    private final List<String> fields = new ArrayList<>();

    public AirportDTO(String... fields) {
        for (String field : fields ) {
            this.fields.add(QuoteUtils.removeQuoteIfExist(field));
        }
    }
    public List<String> getFields(){
        return fields;
    }
}
