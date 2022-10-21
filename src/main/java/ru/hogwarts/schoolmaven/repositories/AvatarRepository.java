package ru.hogwarts.schoolmaven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolmaven.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}