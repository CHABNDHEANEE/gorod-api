INSERT INTO SERVICES (NAME)
VALUES ( 'Жилищно-коммунальные услуги' ),
       ( 'Отопление' ),
       ( 'Вода' ),
       ( 'Холодная вода' ),
       ( 'Горячая вода' ),
       ( 'Детский сад' ),
       ( 'Ясли' ),
       ( 'Старшая группа' ),
       ( 'Электроэнергия' );
INSERT INTO services_children (PARENT_ID, CHILD_ID)
VALUES ( 0, 1 ),
       ( 0, 2 ),
       ( 2, 3 ),
       ( 2, 4 ),
       ( 5, 6 ),
       ( 5, 7 );