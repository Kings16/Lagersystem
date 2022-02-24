package com.occ.demo.Entities;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "lagerartikel")
@Data
@NamedQuery(name= LagerArtikel.FIND_ALL_ARTICLE, query = "select l from LagerArtikel l")
@NamedQuery(name = LagerArtikel.FIND_ARTICLE_BY_ID, query = "select a from LagerArtikel a where a.id = :id")
public class LagerArtikel implements Serializable {

    //static attributes used for EJB
    public static final String FIND_ALL_ARTICLE = "LagerArtikel.findAllAtikel";
    public  static  final String FIND_ARTICLE_BY_ID = "LagerArtikel.findById";
    @Id
    @NotNull(message = "artikelnumber must be set")
    @NotEmpty
    @Column(name = "artikelNummer", nullable = false)
    private String id;

    @NotNull
    @NotEmpty
    @Column(name = "artikelName")
    private String artikelName;

    @NotNull
    @Column(name = "farbe")
    private String farbe;


    @NotNull
    @Column(name = "anzahl")
    private  int anzahl;


}
