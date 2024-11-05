CREATE TABLE class_type (
    id         NUMBER NOT NULL,
    class_type NCHAR(50) NOT NULL
);

ALTER TABLE class_type ADD CONSTRAINT class_type_pk PRIMARY KEY ( id );

CREATE TABLE classroom (
    clussroom_number NUMBER     NOT NULL,
    college_building NCHAR(50)  NOT NULL,
    capacity         NUMBER     NOT NULL,
    class_type_id    NUMBER     NOT NULL,
    class_type       NCHAR(20)  NOT NULL
);

ALTER TABLE classroom ADD CONSTRAINT classroom_pk PRIMARY KEY ( clussroom_number,
                                                                college_building );

CREATE TABLE lecturer (
    id   NUMBER     NOT NULL,
    name NCHAR(100) NOT NULL
);

ALTER TABLE lecturer ADD CONSTRAINT lecturer_pk PRIMARY KEY ( id );

CREATE TABLE subject (
    id NUMBER NOT NULL,
    subject_name           NCHAR(50) NOT NULL,
    lecturer_id            NUMBER    NOT NULL,
    lecturer_name          NCHAR(50) NOT NULL,
    time                   DATE,
    session_count_per_week NUMBER,
    students_count         NUMBER
);

ALTER TABLE subject ADD CONSTRAINT subject_pk PRIMARY KEY ( id );

CREATE TABLE time_lime (
    classroom_clussroom_number NUMBER     NOT NULL,
    classroom_college_building NCHAR(50)  NOT NULL,
    subject_id                 NUMBER     NOT NULL,
    "start"                    DATE,
    "end"                      DATE
);

ALTER TABLE time_lime
    ADD CONSTRAINT sub_class_pk PRIMARY KEY ( classroom_clussroom_number,
                                              classroom_college_building,
                                              subject_id );

ALTER TABLE classroom
    ADD CONSTRAINT classroom_class_type_fk FOREIGN KEY ( class_type_id )
        REFERENCES class_type ( id );

ALTER TABLE time_lime
    ADD CONSTRAINT sub_class_classroom_fk FOREIGN KEY ( classroom_clussroom_number,
                                                        classroom_college_building )
        REFERENCES classroom ( clussroom_number,
                               college_building );

ALTER TABLE time_lime
    ADD CONSTRAINT sub_class_subject_fk FOREIGN KEY ( subject_id )
        REFERENCES subject ( id );

ALTER TABLE subject
    ADD CONSTRAINT subject_lecturer_fk FOREIGN KEY ( lecturer_id )
        REFERENCES lecturer ( id );