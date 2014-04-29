CREATE TABLE LoginUser
(
    Id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Email varchar(100),
    Username varchar(100),
    Password varchar(100),
    CONSTRAINT LoginUser_primary_key PRIMARY KEY (Id)
);

CREATE TABLE TrackingInformation
(
    Id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    UserId int,
    Carrier VARCHAR(100) NOT NULL,
    TrackingNumber VARCHAR(250),
    DestZipCode VARCHAR(25),
    MailingDate DATE,
    CONSTRAINT TrackingInformation_primary_key PRIMARY KEY (Id),
    FOREIGN KEY (UserId) REFERENCES LoginUser (Id)
);

CREATE TABLE TrackingStatus
(
    Id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    StatusDate TIMESTAMP,
    StatusCity varchar(100),
    StatusState varchar(100),
    TrackingInformationId int,
    CONSTRAINT TrackingStatus_primary_key PRIMARY KEY (Id),
    FOREIGN KEY (TrackingInformationId) REFERENCES TrackingInformation (Id)
);