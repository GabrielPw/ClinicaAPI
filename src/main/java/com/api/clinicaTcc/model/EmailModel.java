package com.api.clinicaTcc.model;

import com.api.clinicaTcc.enums.StatusEmailEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name = "TB_EMAIL")
public class EmailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String texto;
    private LocalDateTime dataDeEnvio;
    private StatusEmailEnum status;

}
