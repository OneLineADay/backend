package com.lambda.oladbe.services;

import com.lambda.oladbe.models.Entry;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface EntryService
{
    List<Entry> findAllEntries(Pageable pageable);

    List<Entry> findAllByEntrydate(String entrydate);

    List<Entry> findAllByMonthAndDay(Pageable pageable, String entrydate);

    Entry findEntryById(long id);

    Entry save(Entry entry);

    Entry update(Entry entry, long id);

    void delete(long id);
}
