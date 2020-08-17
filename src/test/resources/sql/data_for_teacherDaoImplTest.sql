    insert into departments(name, description) values('Humanitarian Department', 'FU');
    insert into subjects(name, description, department_id) values('Geography', 'Seeks an understanding of Earth and its human and natural complexities', 1);
	insert into subjects(name, description, department_id) values('Biology', 'Studies life and living organisms', 1);
	insert into subjects(name, description, department_id) values('Chemistry', 'Studies elements and compounds', 1);
	insert into teachers(first_name, last_name, email, phone_number, subject_id, department_id) values('Ivan','Ivanov', 'teacher1@11.ru', '+78989632121', 1, 1);
	insert into teachers(first_name, last_name, email, phone_number, subject_id, department_id) values('Petr','Petrov', 'teacher2@22.ru', '+78989632121', 2, 1);
	insert into teachers(first_name, last_name, email, phone_number, subject_id, department_id) values('Sidor','Sidorov', 'teacher3@33.ru', '+78989632121', 3, 1);
