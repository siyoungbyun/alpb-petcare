INSERT INTO animal_species (name) VALUES ('DOG')
    ON CONFLICT (name) DO NOTHING;
INSERT INTO animal_species (name) VALUES ('CAT')
    ON CONFLICT (name) DO NOTHING;
INSERT INTO animal_species (name) VALUES ('BIRD')
    ON CONFLICT (name) DO NOTHING;
INSERT INTO animal_species (name) VALUES ('RABBIT')
    ON CONFLICT (name) DO NOTHING;
INSERT INTO animal_species (name) VALUES ('REPTILE')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO users (email, name, password) VALUES ('test@gmail.com', '김테스트', '1111')
    ON CONFLICT (email) DO NOTHING;