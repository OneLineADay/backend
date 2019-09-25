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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUserName = userRepository.findByUsername(authentication.getName());

        // create empty array to fill with all users entries
        List<Entry> list = new ArrayList<>();
        entryRepository.findAll().iterator().forEachRemaining(list::add);

        // parse date into month and year variables

        System.out.println("DATE__**___" + entrydate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//        Date dateObj = sdf.parse(entrydate);
//        System.out.println("dateObj" + dateObj);

        // filter to only have entries with date matching a given month
        // filter to only have entries with date matching a given day

//        list.stream().filter((entry) -> entry.getEntrydate() == entrydate);



        return list;
    }

    @Override
    public List<Entry> findAllByMonthAndDay(Pageable pageable, Date entrydate)
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (currentUser != null)
        {
            Entry newEntry = new Entry();
            newEntry.setText(entry.getText());
            newEntry.setEntrydate(new Date());
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
