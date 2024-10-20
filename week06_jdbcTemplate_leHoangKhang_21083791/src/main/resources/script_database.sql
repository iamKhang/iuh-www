CREATE TABLE Candidate (
    id INT PRIMARY KEY AUTO_INCREMENT,
    last_name VARCHAR(50),
    middle_name VARCHAR(50),
    first_name VARCHAR(50),
    dob DATE,
    email VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(15)
);

CREATE TABLE Skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(100),
    description TEXT,
    field VARCHAR(100)
);

CREATE TABLE Job (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT
);

CREATE TABLE Candidate_Skill (
    candidate_id INT,
    skill_id INT,
    level INT CHECK (level BETWEEN 1 AND 5),
    PRIMARY KEY (candidate_id, skill_id),
    FOREIGN KEY (candidate_id) REFERENCES Candidate(id),
    FOREIGN KEY (skill_id) REFERENCES Skill(id)
);

CREATE TABLE Job_Skill (
    job_id INT,
    skill_id INT,
    level_required INT CHECK (level_required BETWEEN 1 AND 5),
    PRIMARY KEY (job_id, skill_id),
    FOREIGN KEY (job_id) REFERENCES Job(id),
    FOREIGN KEY (skill_id) REFERENCES Skill(id)
);

INSERT INTO Candidate (last_name, middle_name, first_name, dob, email, address, phone)
VALUES 
('Nguyen', 'Van', 'A', '1990-01-01', 'vana@example.com', '123 Nguyen Trai, Hanoi', '0123456789'),
('Tran', 'Thi', 'B', '1992-02-15', 'thib@example.com', '456 Le Loi, HCMC', '0987654321'),
('Le', 'Van', 'C', '1985-05-20', 'vanc@example.com', '789 Tran Hung Dao, Da Nang', '0909090909'),
('Pham', 'Thi', 'D', '1995-10-30', 'thid@example.com', '12 Hoang Hoa Tham, Hue', '0912121212'),
('Hoang', 'Minh', 'E', '1998-03-12', 'minhe@example.com', '34 Ba Trieu, Hanoi', '0934343434');

INSERT INTO Skill (skill_name, description, field)
VALUES 
('Java Programming', 'Proficient in Java and its frameworks', 'Software Development'),
('Data Analysis', 'Analyzing large datasets using SQL and Python', 'Data Science'),
('Project Management', 'Managing project timelines and resources', 'Management'),
('Web Development', 'Building modern web applications using HTML, CSS, and JavaScript', 'Software Development'),
('Graphic Design', 'Designing creative graphics for marketing materials', 'Design');

INSERT INTO Job (description)
VALUES 
('Software Developer'),
('Data Analyst'),
('Project Manager'),
('Web Designer'),
('Graphic Designer');

INSERT INTO Candidate_Skill (candidate_id, skill_id, level)
VALUES 
(1, 1, 5),  -- Candidate A has skill in Java Programming with level 5
(1, 4, 4),  -- Candidate A has skill in Web Development with level 4
(2, 2, 3),  -- Candidate B has skill in Data Analysis with level 3
(3, 3, 5),  -- Candidate C has skill in Project Management with level 5
(4, 5, 4),  -- Candidate D has skill in Graphic Design with level 4
(5, 1, 3),  -- Candidate E has skill in Java Programming with level 3
(5, 4, 2);  -- Candidate E has skill in Web Development with level 2

INSERT INTO Job_Skill (job_id, skill_id, level_required)
VALUES 
(1, 1, 4),  -- Software Developer requires Java Programming with level 4
(1, 4, 3),  -- Software Developer requires Web Development with level 3
(2, 2, 3),  -- Data Analyst requires Data Analysis with level 3
(3, 3, 5),  -- Project Manager requires Project Management with level 5
(4, 4, 4),  -- Web Designer requires Web Development with level 4
(5, 5, 4);  -- Graphic Designer requires Graphic Design with level 4


