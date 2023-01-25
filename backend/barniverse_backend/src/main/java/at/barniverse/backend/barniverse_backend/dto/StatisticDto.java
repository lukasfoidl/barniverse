package at.barniverse.backend.barniverse_backend.dto;

/**
 * dto for statistical data, property definitions as well as getter and setter functions
 */
public class StatisticDto {

    private long value;
    private String explenation;

    public StatisticDto(long value, String explenation) {
        this.value = value;
        this.explenation = explenation;
    }

//----getter and setter----

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getExplenation() {
        return explenation;
    }

    public void setExplenation(String explenation) {
        this.explenation = explenation;
    }
}
