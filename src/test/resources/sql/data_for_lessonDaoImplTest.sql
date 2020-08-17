    insert into lectures(name, start_time, end_time) values('I', '08:00', '09:30');
    insert into lectures(name, start_time, end_time) values('II', '10:00', '11:30');
     insert into lectures(name, start_time, end_time) values('III', '12:30', '14:00');

    insert into auditories(name, description, capacity) values('AAA', 'Large auditory', 200);
    insert into auditories(name, description, capacity) values('BBB', 'Small auditory', 50);

    insert into departments(name, description) values('Humanitarian Department', 'FU');

    insert into subjects(name, description, department_id) values('Geography', 'Seeks an understanding of Earth and its human and natural complexities', 1);
	insert into subjects(name, description, department_id) values('Biology', 'Studies life and living organisms', 1);

	insert into teachers(first_name, last_name, email, phone_number, subject_id, department_id) values('Ivan','Ivanov', 'teacher1@11.ru', '+78989632121', 1, 1);
	insert into teachers(first_name, last_name, email, phone_number, subject_id, department_id) values('Petr','Petrov', 'teacher2@22.ru', '+78989632121', 2, 1);

    insert into groups(name) values('AA-11');
	insert into groups(name) values('BB-22');
	insert into groups(name) values('CC-33');

	insert into lessons(teacher_id, subject_id, date, auditory_id, lecture_id) values (1, 1, '2020-12-14', 1, 1);
	insert into lessons(teacher_id, subject_id, date, auditory_id, lecture_id) values (2, 2, '2020-12-14', 2, 2);


	insert into lessons_groups(lesson_id, group_id) values(1, 1);
	insert into lessons_groups(lesson_id, group_id) values(1, 2);
	insert into lessons_groups(lesson_id, group_id) values(2, 3);


