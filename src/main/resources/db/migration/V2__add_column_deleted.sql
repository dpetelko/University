ALTER TABLE faculties
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE departments
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE groups
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE students
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';
