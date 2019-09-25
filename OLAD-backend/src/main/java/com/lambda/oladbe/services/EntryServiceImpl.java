package com.lambda.oladbe.services;

import com.lambda.oladbe.exceptions.ResourceNotFoundException;
import com.lambda.oladbe.models.Entry;
import com.lambda.oladbe.models.User;
import com.lambda.oladbe.repository.EntryRepository;
import com.lambda.oladbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service(value = "entryService")
public class EntryServiceImpl implements EntryService
{

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Entry> findAllEntries(Pageable pageable) throws ResourceNotFoundException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        List<Entry> list = new ArrayList<>();
        entryRepository.findAll(pageable).iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public List<Entry> findAllByEntrydate(String entrydate)
    {
        // create empty array to fill with all users entries
        List<Entry> list = new ArrayList<>();
        entryRepository.findAll().iterator().forEachRemaining(list::add);

        // parse query param date into day, month variables
        String[] arr = entrydate.split("-", 3);
        String day = arr[0];
        String month = arr[1];

        // create another empty arr and add entries that match date and month
        List<Entry> outputList = new ArrayList<>();
        for (Entry e : list) {
            String[] eStrArr = e.getEntrydate().split("-", 3);
            String d = eStrArr[0];
            String m = eStrArr[1];
            if (d.equals(day) && m.equals(month)) {
                outputList.add(e);
            }
        }
        return outputList;
    }


    @Override
    public List<Entry> findAllByMonthAndDay(Pageable pageable, String entrydate)
    {

        return null;
    }

    @Override
    public Entry findEntryById(long id) throws ResourceNotFoundException
    {
        return entryRepository.findById(id)
                     .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Transactional
    @Override
    public Entry save(Entry entry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);

        if (currentUser != null)
        {
            Entry newEntry = new Entry();
            newEntry.setText(entry.getText());
            newEntry.setEntrydate(strDate);
            newEntry.setUser(currentUser);
            return entryRepository.save(newEntry);
        } else
        {
            throw new ResourceNotFoundException(" Not current user");
        }
    }

    @Override
    public Entry update(Entry entry, long id) throws ResourceNotFoundException
    {
        Entry newEntry = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (entry.getText() != null)
        {
            newEntry.setText(entry.getText());
        }
        if (entry.getEntrydate() != null)
        {
            newEntry.setEntrydate(entry.getEntrydate());
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
