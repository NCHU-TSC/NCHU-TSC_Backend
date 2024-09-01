INSERT IGNORE INTO `role` (`name`, `can_modify_bank_record`, `can_modify_case`, `can_modify_case_order`, `can_modify_member`, `can_modify_role`, `can_modify_system_variable`, `can_view_bank_record`, `can_view_case`, `can_view_case_order`, `can_view_log`, `can_view_member`, `can_view_role`, `can_view_system_variable`, `need_pay_to_join`, `need_pay_to_apply_case`, `system_account`) VALUES
	('來賓', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'1', b'1', b'0'),
	('社長', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'0', b'0', b'0'),
	('系統', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'0', b'0', b'1');

INSERT IGNORE INTO `system_variable` (`key`, `default_value`, `description`, `type`, `value`) VALUES
	('default_role', '來賓', '註冊後的預設身分組', 'STRING', '來賓'),
	('first_member_role', '社長', '第一位社員的身分組', 'STRING', '社長'),
	('general_member_valid_interval', '31622400', '一般社員有效期限', 'INTEGER', '31622400'),
	('redirecting_expire_seconds', '86400', '重導向連結的有效期限', 'INTEGER', '86400');