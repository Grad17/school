- liquibase formatted sql

- changeset sk:1
CREATE INDEX student_n_idx ON student(name);

- changeset sk:2
CREATE INDEX faculty_nc_idx ON faculty(name, color);
