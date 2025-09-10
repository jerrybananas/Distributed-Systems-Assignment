package org.hua.dit.dsproject.pet;

import javax.persistence.*;
// Note: Using javax.persistence for JPA validation instead of javax.validation for Spring Boot 2.6.1 compatibility
import java.util.Date;

/**
 * @author it21542 - Antonis Rouseas
 * Pet entity for the Pet Registry System
 */
@Entity(name = "PET")
@Table
public class Pet {
    @Id
    @Column( name = "SERIAL_NUMBER")
    //@SequenceGenerator(name = "pet_sequence", sequenceName = "pet_sequence", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_sequence")
    private int serialNumber;
    @Column( name = "RACE")
    private String race;
    @Column( name = "SEX")
    private char sex;
    @Column( name = "BIRTHDAY")
    private Date birthday;
    @Column( name = "NAME")
    private String name;
    @Column( name = "PET_ID_STR")
    private String petIdStr;
    @Column( name = "OWNER_ID")
    private String ownerID;
    @Column( name = "MEDICAL_HISTORY", columnDefinition = "TEXT")
    private String medical_history;
    @Column( name = "VET_APPROVED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean vet_approved;


    public Pet() {
        this.vet_approved = false; // Default to not approved
    }

    public Pet(int serialNumber, String name, String race, char sex, Date birthday, String ownerID) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.race = race;
        this.sex = sex;
        this.birthday = birthday;
        this.ownerID = ownerID;
        this.medical_history = "empty";
        this.petIdStr = String.format("%09d", serialNumber);
        this.vet_approved = false; // Default to not approved
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getMedical_history() {
        return medical_history;
    }

    public void setMedical_history(String medical_history) {
        this.medical_history = medical_history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPetIdStr() {
        return petIdStr;
    }

    public void setPetIdStr(String petIdStr) {
        this.petIdStr = petIdStr;
    }

    public Boolean getVet_approved() {
        return vet_approved;
    }

    public void setVet_approved(Boolean vet_approved) {
        this.vet_approved = vet_approved;
    }
}
