INSERT INTO users (email, name, password, phone_number, is_admin) VALUES ('a@gmail.com', '테스트', '1111', '01012341234', false)
    ON CONFLICT (email) DO NOTHING;