package com.lambda.oladbe.services;

import com.lambda.oladbe.models.Entry;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface EntryService
{
    List<Entry> findAll(Pageable pageable);

    List <Entry> findAllByEntrydate(Date entrydate);

    Entry save(Entry entry);

    Entry update(Entry entry, long id);

    void delete(long id);
}
