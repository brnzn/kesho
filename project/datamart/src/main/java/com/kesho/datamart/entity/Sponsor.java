package com.kesho.datamart.entity;

import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SPONSORS")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ACTIVE", columnDefinition = "BIT")
    private Boolean active;

    @Column(name = "ANONYMOUS", columnDefinition = "BIT")
    private Boolean anonymous;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PAYEE_TYPE")
    @Enumerated(EnumType.STRING)
    private PayeeType payeeType;

    @Column(name = "HOW_SPONSOR_FOUND_US")
    @Enumerated(EnumType.STRING)
    private FoundUs howFoundUs;

    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @Column(name = "LEVEL_OF_PARTICIPATION")
    @Enumerated(EnumType.STRING)
    private LevelOfParticipation levelOfParticipation;


}
