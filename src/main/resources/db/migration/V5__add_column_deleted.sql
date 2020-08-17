ALTER TABLE teachers
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE subjects
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';
