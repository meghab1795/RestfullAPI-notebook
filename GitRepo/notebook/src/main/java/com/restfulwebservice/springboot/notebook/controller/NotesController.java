package com.restfulwebservice.springboot.notebook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.restfulwebservice.springboot.notebook.exception.ResourceNotFoundException;
import com.restfulwebservice.springboot.notebook.model.Note;
import com.restfulwebservice.springboot.notebook.repository.NotesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1")
public class NotesController {
	
	

@Autowired
private NotesRepository notesrepository;

//retrieve all notes
@GetMapping("/notes")
public List<Note> getAllNotes(){
	return notesrepository.findAll();
}

//create notes
@PostMapping("/notes")
public Note createNotes(@Valid @RequestBody Note note) {
	return notesrepository.save(note);
	
}

//retrieve by id
@GetMapping("/notes/{id}")
public ResponseEntity<Note> getNoteByID(@PathVariable(value = "id") long noteId) throws ResourceNotFoundException{
	Note note = notesrepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note not found for this id :: " + noteId));
	return ResponseEntity.ok().body(note);
}

//update notes
@PutMapping("/notes/{id}")
public ResponseEntity<Note> updateNote(@PathVariable(value = "id") Long noteId,
		@Valid @RequestBody Note noteDetails) throws ResourceNotFoundException {
	Note note = notesrepository.findById(noteId)
			.orElseThrow(() -> new ResourceNotFoundException("Note not found for this id :: " + noteId));

	note.setBody(noteDetails.getBody());
	note.setModifiedTimestamp(noteDetails.getModifiedTimestamp());
	
	final Note updatedNote = notesrepository.save(note);
	return ResponseEntity.ok(updatedNote);
	
}

//delete notes

@DeleteMapping("/notes/{id}")
public Map<String, Boolean> deleteNotes(@PathVariable(value = "id") Long noteId)
		throws ResourceNotFoundException {
	Note note = notesrepository.findById(noteId)
			.orElseThrow(() -> new ResourceNotFoundException("Note not found for this id :: " + noteId));

	notesrepository.delete(note);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return response;
}


	
}
