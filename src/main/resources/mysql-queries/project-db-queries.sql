create database pet_empire_animal_hospital;
use pet_empire_animal_hospital;
create table pet(
                    pet_id varchar(100) primary key,
                    pet_name varchar(200),
                    pet_breed varchar(250),
                    pet_weight double,
                    pet_colour varchar(200)
);
create table client(
                       client_id varchar(100) primary key,
                       pet_id varchar(100),
                       client_name varchar(300),
                       client_address varchar(500),
                       client_email varchar(300),
                       client_contact_no int,
                       foreign key (pet_id) references pet (pet_id) on delete cascade on update cascade
);
create table appointment(
                            ap_id varchar(100) primary key,
                            client_id varchar(100),
                            ap_no int,
                            ap_date  date,
                            ap_time time,
                            foreign key (client_id) references client (client_id) on delete cascade on update cascade
);
create table veterinarian(
                             veterinarian_id varchar(100) primary key,
                             veterinarian_name varchar(300),
                             veterinarian_contact_no int,
                             veterinarian_email varchar(300)
);
create table user(
                     user_id varchar(100) primary key,
                     password varchar(300),
                     user_name varchar(300),
                     veterinarian_id varchar(100),
                     foreign key (veterinarian_id) references veterinarian (veterinarian_id) on delete cascade on update cascade
);
create table prescription(
                             p_id varchar(100) primary key,
                             p_type varchar(300),
                             veterinarian_id varchar(100),
                             foreign key (veterinarian_id) references veterinarian(veterinarian_id) on delete cascade on update cascade
);
create table treatments(
                           t_id varchar(100) primary key,
                           t_type varchar(300),
                           t_description varchar(500)
);
create table prescription_treatment(
                                       p_id varchar(100),
                                       t_id varchar(100),
                                       treatment_price double,
                                       pt_date date,
                                       pt_time time,
                                       foreign key (p_id) references prescription (p_id) on delete cascade on update cascade,
                                       foreign key (t_id) references treatments (t_id) on delete cascade on update cascade
);
create table medicine(
                         m_id varchar(100) primary key,
                         m_name varchar(300),
                         m_type varchar(400),
                         m_price double,
                         m_description varchar(500),
                         m_qty_on_hand int
);
create table prescription_medicine(
                                      m_id varchar(100),
                                      p_id varchar(100),
                                      foreign key (m_id) references medicine (m_id) on delete cascade on update cascade,
                                      foreign key (p_id) references prescription (p_id) on delete cascade on update cascade
);
create table supplier(
                         s_id varchar(100) primary key,
                         s_name varchar(300),
                         s_contact_no int,
                         s_location varchar(300),
                         s_email varchar(300),
                         product_type varchar(300),
                         qty_on_hand int
);
create table medicine_supplier(
                                  s_id varchar(100),
                                  m_id varchar(100),
                                  supply_date date,
                                  foreign key (s_id) references supplier (s_id) on delete cascade on update cascade,
                                  foreign key (m_id) references medicine (m_id) on delete cascade on update cascade

);

create table bills(
                      bill_id varchar(100) primary key,
                      client_id varchar(100),
                      date date,
                      foreign key (client_id) references client (client_id) on delete cascade on update cascade
);
create table bills_details(
                              bill_id varchar(100),
                              m_id varchar(100),
                              m_qty int,
                              m_price double,
                              foreign key (bill_id) references bills (bill_id) on delete cascade on update cascade,
                              foreign key (m_id) references medicine (m_id) on delete cascade on update cascade
);




