---------- For Place Bill ------------

SELECT *
FROM pet_empire_animal_hospital.medicine m
         INNER JOIN pet_empire_animal_hospital.bills_details bd ON m.m_id = bd.m_id
         INNER JOIN pet_empire_animal_hospital.bills b ON bd.bill_id = b.bill_id
WHERE b.bill_id = $P{BillID}
ORDER BY b.bill_id;

---------------------------------------

----------- For Appointment and etc... ---------------

SELECT * FROM  pet_empire_animal_hospital.appointment;

-------------------------------------------