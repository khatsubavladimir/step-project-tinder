INSERT INTO users (username, password_, photoUrl) VALUES ('Sheldon', 'aguiiafu74', 'https://www.vokrug.tv/pic/person/a/7/7/e/a77e7c2fad04c812869cb7fa9bdfd01c.jpeg');
INSERT INTO users (username, password_, photoUrl) VALUES ('Emi', 'ohg2ug8', 'https://prm.ua/wp-content/uploads/2018/03/Emi-Fara-Fauler.jpeg');
INSERT INTO users (username, password_, photoUrl) VALUES ('Penni', 'gFoF82Fadp', 'https://today.ua/wp-content/uploads/2022/09/2_EH_CHP_200220The-Big-Bang-Theory_5680JPG.jpg');

INSERT INTO messages (from_id, to_id, message) VALUES (1, 2, 'hi');
INSERT INTO messages (from_id, to_id, message) VALUES (2, 1, 'hello');
INSERT INTO messages (from_id, to_id, message) VALUES (1, 3, 'hi');

INSERT INTO liked (user_id, pf_id) VALUES (3, 1);
INSERT INTO liked (user_id, pf_id) VALUES (3, 2);