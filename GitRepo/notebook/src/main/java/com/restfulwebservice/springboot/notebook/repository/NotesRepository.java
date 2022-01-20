package com.restfulwebservice.springboot.notebook.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.restfulwebservice.springboot.notebook.model.Note;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
	
}
