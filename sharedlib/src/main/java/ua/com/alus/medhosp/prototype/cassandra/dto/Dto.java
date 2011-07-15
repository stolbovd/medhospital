package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Dto enum for cassandra (client application)
 */
public enum Dto {
    PATIENT_ATTRIBUTE_VALUE("PatientAttributeValue"), PATIENT("PatientDTO");

    Dto(String dtoName){
        this.dtoName = dtoName;
    }
    private String dtoName;

    public String getDtoName() {
        return dtoName;
    }
}


