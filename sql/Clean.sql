DELETE FROM works_in_devis;
DELETE FROM works_predefined_by_building_type;
DELETE FROM works;
DELETE FROM works_in_devis_details;
DELETE FROM payement;
DELETE FROM devis_set_details;
DELETE FROM devis;
DELETE FROM building_type_description;
DELETE FROM finition_by_building_type;
DELETE FROM building_type;
DELETE FROM building_finition;
DELETE FROM unit;
DELETE FROM refresh_token;
DELETE FROM lieu;
DELETE FROM _user
WHERE _user.roles = 'USER';