INSERT INTO users (email, name, password, phone_number, is_admin) VALUES ('test@gmail.com', '테스트', '$2a$10$p16OTcTxwM6F2DLa1IYY8uOU37Su43xQuzL13d1QqN1CiApbSnMO2', '01012341234', false)
    ON CONFLICT (email) DO NOTHING;

INSERT INTO code_group (code_group_id, created_at, description, name, updated_at) VALUES ('ANIMAL', CURRENT_TIMESTAMP, '반려동물 카테고리입니다. 삭제할 시, 펫시터 서비스 등이 정상적으로 작동하지 않을 수도 있습니다.', '동물구분', CURRENT_TIMESTAMP);

INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('CAT', 1, CURRENT_TIMESTAMP, true, '고양이', 1, CURRENT_TIMESTAMP, 'CAT');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('DOG', 1, CURRENT_TIMESTAMP, true, '개', 1, CURRENT_TIMESTAMP, 'DOG');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('TURTLE', 1, CURRENT_TIMESTAMP, true, '거북이', 1, CURRENT_TIMESTAMP, 'TURTLE');