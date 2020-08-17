ALTER TABLE lectures
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE auditories
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';

ALTER TABLE lessons
ADD COLUMN deleted boolean NOT NULL DEFAULT 'false';
