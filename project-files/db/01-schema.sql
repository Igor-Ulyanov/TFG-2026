-- USERS: Saves user login including hashed password
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    pass_hash TEXT NOT NULL
);

-- ORGANIZATIONS: Org. structure, contains a foreign key of itself to allow for a layer structure
CREATE TABLE organizations (
    ID SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id INT,

    CONSTRAINT fk_org_parent
        FOREIGN KEY (parent_id)
        REFERENCES organizations(id)
        ON DELETE SET NULL  --Orphans child org is parent is deleted, allows easier restructuring
);

-- ROLES: Description of which roles belong to which orgs
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    organization_id INT NOT NULL,

    CONSTRAINT fk_roles_org
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id)
        ON DELETE CASCADE, --if an org is deleted, its roles go with it

    CONSTRAINT unique_role_for_org
        UNIQUE (name, organization_id) --an org cant have two roles with the same name
);

-- PERMISSIONS: Preset and unmodifiable set of permissions that can be bundled into roles
CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- ROLES_PERMISSIONS: table describing N-N relation between roles and permissions
CREATE TABLE roles_permissions (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,

    PRIMARY KEY (role_id, permission_id),

    --If either the permission or role is deleted the realtion expires with it (CASCADE)

    CONSTRAINT fk_rp_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_rp_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id)
        ON DELETE CASCADE
);

-- USER_ORG_ROLE: table describing N-N relation between a user and the roles they have in a given org
CREATE TABLE user_org_role (
    user_id INT NOT NULL,
    organization_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, organization_id, role_id),

    --If either the permission, role or org is deleted the realtion expires with it (CASCADE)

    CONSTRAINT fk_uor_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_uor_org
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_uor_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);


-- Indexes for improved search performance

--CREATE INDEX idx_users_username ON users(username);
--CREATE INDEX idx_users_email ON users(email);
--
--CREATE INDEX idx_roles_org ON roles(organization_id);
--
--CREATE INDEX idx_uor_user ON user_org_role(user_id);
--CREATE INDEX idx_uor_org ON user_org_role(organization_id);
--CREATE INDEX idx_uor_role ON user_org_role(role_id);
--
--CREATE INDEX idx_rp_role ON roles_permissions(role_id);
--CREATE INDEX idx_rp_permission ON roles_permissions(permission_id);