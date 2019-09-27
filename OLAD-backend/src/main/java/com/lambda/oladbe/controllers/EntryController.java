package com.lambda.oladbe.controllers;


import com.lambda.oladbe.models.Entry;
import com.lambda.oladbe.models.ErrorDetail;
import com.lambda.oladbe.services.EntryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EntryController
{

    @Autowired
    private EntryService entryService;

    // GET http://localhost:2019/entries/?page=0&size=3
    @ApiOperation(value = "returns all entries", response = Entry.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page number"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "integer", paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc)" + "Default sort order is ascending " + "Multiple sort criteria are supported)")})
    @GetMapping(value = "/entries", produces = {"application/json"})
    public ResponseEntity<?> listAll(@PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        List<Entry> myEntries = entryService.findAllEntries(pageable);
        return new ResponseEntity<>(myEntries, HttpStatus.OK);
    }



    // GET http://localhost:2019/entries/date/25-09-2019
    @ApiOperation(value = "returns all user entries by date", response = Entry.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", dataType = "integer", paramType = "query", value = "Results page number"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entries Found", response = Entry.class),
            @ApiResponse(code = 204, message = "Server found no content", response = Entry.class),
            @ApiResponse(code = 404, message = "Entries Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/entries/date/{date}", produces = {"application/json"})
        public ResponseEntity<?> listAllEntriesByDate(@PathVariable String date)
        {
            List<Entry> myEntriesByDate = entryService.findAllByEntrydate(date);
            return new ResponseEntity<>(myEntriesByDate, HttpStatus.OK);
        }



    // GET localhost:2019/entry/{id}
    @ApiOperation(value = "returns entry by given id", response = Entry.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry Found", response = Entry.class),
            @ApiResponse(code = 204, message = "No content found on server", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/entry/{id}", produces = {"application/json"})
    public ResponseEntity<?> getEntryById(
            @ApiParam(value = "Entry Id", required = true, example = "1")
            @PathVariable long id)
        {
            Entry singleEntry = entryService.findEntryById(id);
            return new ResponseEntity<>(singleEntry, HttpStatus.OK);
        }


    // POST localhost:2019/entry - save a new entry
    @ApiOperation(value = "Create a new Entry", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating Entry", response = ErrorDetail.class)})
    @PostMapping(value = "/entry", consumes = {"application/json"})
    public ResponseEntity<?> addNewEntry(@Valid @RequestBody Entry newEntry) throws URISyntaxException
    {
        newEntry = entryService.save(newEntry);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newEntryURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{entryid}").buildAndExpand(newEntry.getEntryid()).toUri();
        responseHeaders.setLocation(newEntryURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    // PUT localhost:2019/entry/{entryid} - update entry text
    @ApiOperation(value = "updates entry by given id", response = Entry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry Found", response = Entry.class),
            @ApiResponse(code = 204, message = "Server found no content", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)})
    @PutMapping(value = "/entry/{id}")
    public ResponseEntity<?> updateEntry(
            @RequestBody Entry updatedEntry,
            @ApiParam(value = "Entry Id", required = true, example = "1")
            @PathVariable long id)
    {
        entryService.update(updatedEntry, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // DELETE localhost:2019/entry/{id}
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry Deleted", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)})
    @DeleteMapping("/entry/{id}")
    public ResponseEntity<?> deleteEntryById(
            @ApiParam(value = "Author Id", required = true, example = "1") @PathVariable long id)
    {
        entryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
