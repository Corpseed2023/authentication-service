package com.authentication.model;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "subscriptions")
public class Subscription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_date", nullable = false)
    private Date startDate;


    @Column(name = "end_date", nullable = false)
    private Date endDate;


    public Subscription(User user, Date startDate, Date endDate) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Subscription() {


    }
}
