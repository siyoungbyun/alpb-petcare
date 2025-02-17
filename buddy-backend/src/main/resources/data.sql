INSERT INTO users (email, name, password, phone_number, is_admin) VALUES ('test@gmail.com', '테스트', '$2a$10$p16OTcTxwM6F2DLa1IYY8uOU37Su43xQuzL13d1QqN1CiApbSnMO2', '01012341234', false)
    ON CONFLICT (email) DO NOTHING;

INSERT INTO code_group (code_group_id, created_at, description, name, updated_at) VALUES ('ANIMAL', CURRENT_TIMESTAMP, '반려동물 카테고리입니다. 삭제할 시, 펫시터 서비스 등이 정상적으로 작동하지 않을 수도 있습니다.', '동물구분', CURRENT_TIMESTAMP);

INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('CAT', 1, CURRENT_TIMESTAMP, true, '고양이', 1, CURRENT_TIMESTAMP, 'CAT');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('DOG', 1, CURRENT_TIMESTAMP, true, '개', 1, CURRENT_TIMESTAMP, 'DOG');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('TURTLE', 1, CURRENT_TIMESTAMP, true, '거북이', 1, CURRENT_TIMESTAMP, 'TURTLE');

INSERT INTO code_group (code_group_id, created_at, description, name, updated_at) VALUES ('SERVICE', CURRENT_TIMESTAMP, '펫시터 서비스 카테고리입니다. 삭제할 시, 펫시터 서비스 등이 정상적으로 작동하지 않을 수도 있습니다.', '서비스구분', CURRENT_TIMESTAMP);

INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('WALK', 2, CURRENT_TIMESTAMP, true, '산책', 1, CURRENT_TIMESTAMP, 'WALK');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('FOSTER', 2, CURRENT_TIMESTAMP, true, '위탁', 1, CURRENT_TIMESTAMP, 'FOSTER');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('FEED', 2, CURRENT_TIMESTAMP, true, '밥주기', 1, CURRENT_TIMESTAMP, 'FEED');

INSERT INTO code_group (code_group_id, created_at, description, name, updated_at) VALUES ('AREA', CURRENT_TIMESTAMP, '돌봄 가능 지역 카테고리입니다. 삭제할 시, 펫시터 서비스 등이 정상적으로 작동하지 않을 수도 있습니다.', '지역구분', CURRENT_TIMESTAMP);

INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('SEOUL', 3, CURRENT_TIMESTAMP, true, '서울', 1, CURRENT_TIMESTAMP, 'SEOUL');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('INCHEON', 3, CURRENT_TIMESTAMP, true, '경기', 1, CURRENT_TIMESTAMP, 'INCHEON');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('DAEGU', 3, CURRENT_TIMESTAMP, true, '대구', 1, CURRENT_TIMESTAMP, 'DAEGU');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('GWANGJU', 3, CURRENT_TIMESTAMP, true, '광주', 1, CURRENT_TIMESTAMP, 'GWANGJU');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('DAEJEON', 3, CURRENT_TIMESTAMP, true, '대전', 1, CURRENT_TIMESTAMP, 'DAEJEON');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('ULSAN', 3, CURRENT_TIMESTAMP, true, '울산', 1, CURRENT_TIMESTAMP, 'ULSAN');
INSERT INTO code_detail (code_detail_id, code_group_internal_id, created_at, is_active, name, sort_order, updated_at, value) VALUES ('SEJONG', 3, CURRENT_TIMESTAMP, true, '세종', 1, CURRENT_TIMESTAMP, 'SEJONG');

