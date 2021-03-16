CREATE DATABASE [StrategyProcess];

CREATE TABLE StrategyProcess.dbo.StrategyDroolsResult (
	id bigint NULL,
	actionTypeId varchar(100) NULL,
	status varchar(100) NULL,
	segmentationId int NULL
) ;

CREATE TABLE StrategyProcess.dbo.ActionType (
	id bigint NULL,
	symbol varchar(100) NULL
) ;

CREATE TABLE StrategyProcess.dbo.Document (
	id bigint NULL,
	strategyRecordId int NULL,
	status varchar(100) NULL
) ;

