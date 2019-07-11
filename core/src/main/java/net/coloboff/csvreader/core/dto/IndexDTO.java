package net.coloboff.csvreader.core.dto;

public class IndexDTO {
    private final String fieldValue;
    private final Long offset;

    public IndexDTO(String fieldValue, Long offset) {
        this.fieldValue = fieldValue;
        this.offset = offset;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Long getOffset() {
        return offset;
    }
}
