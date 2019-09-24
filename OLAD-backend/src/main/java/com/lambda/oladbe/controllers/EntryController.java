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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
//    @ApiOperation(value = "returns all entries", response = Entry.class, responseContainer = "List")
//    @ApiImplicitParams({@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
//            value = "Results page number"), @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
//            value = "Number of records per page"), @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "integer", paramType = "query",
//            value = "Sorting criteria in the format: property(asc|desc)" + "Default sort order is ascending " + "Multiple sort criteria are supported)")})
//    @GetMapping(value = "/entries/", produces = {"application/json"})
//    public ResponseEntity<?> listAll(@PageableDefault(page = 0, size = 10) Pageable pageable)
//    {
//        List<Entry> myEntries = entryService.findAll(pageable);
//        return new ResponseEntity<>(myEntries, HttpStatus.OK);
//    }

    // GET http://localhost:2019/entries
//    @ApiOperation(value = "returns all user entries", response = Entry.class, responseContainer = "List")
//    @ApiResponses(
//            value = {@ApiResponse(code = 200, message = "Entries Found", response = Entry.class), @ApiResponse(code = 204,
//                    message = "Server found no content", response = Entry.class), @ApiResponse(code = 404,
//                    message = "Entries Not Found", response = ErrorDetail.class)})
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @GetMapping(value = "/entries", produces = {"application/json"})
//    public ResponseEntity<?> listAllEntries()
//    {
//        List<Entry> myEntries = entryService.findAll(Pageable.unpaged());
//        return new ResponseEntity<>(myEntries, HttpStatus.OK);
//    }


    //    // PUT localhost:2019/entries/{id}
    //    @ApiOperation(value = "returns entry by given id", response = Entry.class, responseContainer = "List")
    //    @ApiResponses(value = {
    //            @ApiResponse(code = 200, message = "Entry Found", response = Entry.class),
    //            @ApiResponse(code = 204, message = "Server found no content", response = Entry.class),
    //            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)
    //    })
    //    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //    @PutMapping(value = "/data/entries/{id}")
    //    public ResponseEntity<?> getEntryById(
    //            @ApiParam(value = "Entry Id", required = true, example = "1")
    //            @PathVariable long id) {
    //        entryService.findById(id);
    //        return new ResponseEntity<>(HttpStatus.OK);
    //    }


    // POST localhost:2019/entry - save a new entry
    @ApiOperation(value = "Create a new Entry", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating Entry", response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_USER')")
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


    // PUT localhost:2019/data/entry/{id} - update entry text
    @ApiOperation(value = "updates entry by given id", response = Entry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry Found", response = Entry.class),
            @ApiResponse(code = 204, message = "Server found no content", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(value = "/entry/{id}")
    public ResponseEntity<?> updateEntry(
            @RequestBody Entry updateEntry,
            @ApiParam(value = "Entry Id", required = true, example = "1")
            @PathVariable long id)
    {
        entryService.update(updateEntry, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE localhost:2019/entry/{id}
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry Deleted", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry Not Found", response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/entry/{id}")
    public ResponseEntity<?> deleteEntryById(
            @ApiParam(value = "Author Id", required = true, example = "1") @PathVariable long id)
    {
        entryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
