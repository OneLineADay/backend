package com.lambda.oladbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.String;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entry")
public class Entry extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long entryid;

    @Column(nullable = false)
    private String entrydate;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties({"entry", "hibernateLazyInitializer"})
    private User user;

    public Entry() { }

    public Entry(String entrydate, String text, User user)
    {
        this.entrydate = entrydate;
        this.text = text;
        this.user = user;
    }


    public long getEntryid() { return entryid; }

    public void setEntryid(long entryid)
    {
        this.entryid = entryid;
    }

    public String getEntrydate() { return entrydate; }

    public void setEntrydate(String entrydate) { this.entrydate = entrydate; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    @Override
    public String toString()
    {
        return "Userentry{" + "entryid=" + entryid + ", text=" + text + ", date=" + entrydate + "user=" + user.getUsername() + '}';
    }
}
