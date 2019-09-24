package com.lambda.oladbe.services;

import com.lambda.oladbe.exceptions.ResourceNotFoundException;
import com.lambda.oladbe.models.Entry;
import com.lambda.oladbe.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "entryService")
public class EntryServiceImpl implements EntryService
{

    @Autowired
    private EntryRepository entryRepository;

    public List<Entry> findAll(Pageable pageable) throws ResourceNotFoundException
    {
        List<Entry> list = new ArrayList<>();
        entryRepository.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Entry> findAllByEntrydate(Date entrydate)
    {
        return null;
    }

    //    @Override
//    public Entry findById(long id) throws ResourceNotFoundException
//    {
//        return entryRepository.findById(id);
//        //                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
//    }

    @Transactional
    @Override
    public Entry save(Entry entry)
    {
        Entry newEntry = new Entry();
        newEntry.setText(entry.getText());
        return entryRepository.save(newEntry);
    }

    @Override
    public Entry update(Entry entry, long id) throws ResourceNotFoundException
    {
        Entry newEntry = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (entry.getText() != null)
        {
            newEntry.setText(entry.getText());
        }

        return entryRepository.save(newEntry);
    }


    @Override
    public void delete(long id)
    {
        if (entryRepository.findById(id).isPresent())
        {
            entryRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

}
