package com.pknu.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="data_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Model {

    @Id
    private Integer DATA_ID;

    @Column(name= "MEM_ID")
    private String mem_id;
    
    private LocalDate CHECK_DATE;
    private Integer DI1_DG;
    private Integer DI2_DG;
    private Integer DE1_DG;
    private Integer DI3_DG;
    private Integer HE_HP;
    private double HE_GLU;
    private double HE_HBA1C;
    private double HE_CHOL;
    private double HE_BMI;
    private double HE_WC;
    private double BS1_1;
    private double BD1_11;
    private double BD2_1;
    private double PA_AEROBIC;
    private double BE8_1;
    private double SEX;
    private double AGE;
    private double EDU;
    private double INCM;
    private double PREDICT;
    
}
