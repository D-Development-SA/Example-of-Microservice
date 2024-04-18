INSERT INTO WASTE_MANAGER_ADDRESS (id,address,created_date,is_enabled,last_modified_date,version)
VALUES
    (1,'5399 Quis Avenue','2024-09-06 14:39:05.005','false','2024-12-27 16:53:40.040',3),
    (2,'774-2700 Eu Avenue','2024-11-06 10:31:08.008','true','2023-06-15 12:03:18.018',8),
    (3,'P.O. Box 925, 4660 Vestibulum Av.','2024-05-11 20:21:00.000','true','2024-11-27 03:57:20.020',4),
    (4,'262-8747 Tincidunt Ave','2024-05-17 21:32:52.052','false','2023-12-26 21:32:18.018',5),
    (5,'563 Consequat, St.','2024-09-03 14:20:18.018','true','2024-02-04 17:25:24.024',13);
ALTER TABLE WASTE_MANAGER_ADDRESS ALTER COLUMN id RESTART WITH 6;
