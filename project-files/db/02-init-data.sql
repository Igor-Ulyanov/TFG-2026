--BASE PERMISSIONS: This is the static list of permissions assignable to roles
INSERT INTO permissions (name, description) VALUES
('CREATE_USER', 'Create new users'),
('READ_USER', 'Read user data'),
('UPDATE_USER', 'Update user data'),
('DELETE_USER', 'Delete users'),

('CREATE_ORG', 'Create organizations'),
('READ_ORG', 'Read organization data'),
('UPDATE_ORG', 'Update organization data'),
('DELETE_ORG', 'Delete organizations'),

('CREATE_ROLE', 'Create roles'),
('READ_ROLE', 'Read roles'),
('UPDATE_ROLE', 'Update roles'),
('DELETE_ROLE', 'Delete roles'),

('ASSIGN_ROLE', 'Assign roles to users');

--SYSTEM ORG: this is a global org, only the db admin will be assigned to it
INSERT INTO organizations (name, description)
VALUES ('SYSTEM', 'Internal system organization');

--MASTER ROLE: has control over the entire database
INSERT INTO roles (name, description, organization_id)
VALUES ('MASTER', 'System administrator with full access', 1);

--assign all premissions to master role
INSERT INTO roles_permissions (role_id, permission_id)
SELECT 1, id FROM permissions;

--create master user
INSERT INTO users (username, email, pass_hash)
VALUES ('MASTER', 'master@system.com', '$2a$12$RKdWE7K.G/g8EB3yukQsAeLSlwL7NEVi1AYp1.OpCADxdyZAPj.sK'); --MASTER PASSWORD

--assign role to master user in system org.
INSERT INTO user_org_role (user_id, organization_id, role_id)
VALUES (1, 1, 1);