DELETE FROM employee;
DELETE FROM department;

INSERT INTO department (id, name) VALUES
(1, 'Ke Toan'),
(2, 'Tai Chinh'),
(3, 'Tap Vu'),
(4, 'Hanh Chinh');

INSERT INTO employee (id, date_of_birth, email, employee_code, full_name, gender, phone_number, department_id) VALUES
(2, '2003-03-04', 'iamhoangkhang@icloud.com', '4565230611', 'Le Hoang Khang', true, '0383741660', 1),
(3, '2003-03-11', 'vuquochuy@gmail.com', '4562324674', 'Vu Quoc Huy', true, '0345768354', 2),
(4, '2003-05-06', 'dinhtranphukhang@gmail.com', '4560502935', 'Dinh Tran Phu Khang', true, '0975864330', 1);

ALTER TABLE department AUTO_INCREMENT = 5;
ALTER TABLE employee AUTO_INCREMENT = 5;
