------------------written by Rugal-Bernstein
------------------2011-04-18
------------------create user
conn / as sysdba
create user yuri identified by yuri;
grant connect,resource to yuri;
conn yuri/yuri


-------------------------------------------------create table




-- Create table
create table admin
(
  aid      char(10) not null,
  password varchar2(20) not null,
  name     varchar2(20) not null,
  rank     number(1) not null,
  addtime  date
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table admin
  add constraint PK_AID primary key (AID);
-- Create/Recreate check constraints 
alter table admin
  add constraint CK_RANK
  check (rank in(0,1,2));



-- Create table
create table provider
(
  pid       char(10) not null,
  telephone varchar2(20) not null,
  address   varchar2(50) not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table provider
  add constraint PK_PID primary key (PID);





-- Create table
create table equipment
(
  eid     char(10) not null,
  type    varchar2(20) not NULL,
  pid     char(10) not null,
  price   number(8,1) not null,
  buytime date not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table equipment
  add constraint PK_EID primary key (EID);
alter table equipment
  add constraint FK_equipment_provider foreign key (PID)
  references provider (PID);







-- Create table      
create table log_bug 
(
  lbid       char(10) not null,
  eid        char(10) not null, 
	price     NUMBER(5,1) NOT NULL ,
  notation   varchar2(50) not NULL
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table log_bug
  add constraint PK_LBID primary key (lbid);
alter table log_bug
  add constraint FK_logbug_equipment foreign key (EID)
  references equipment (EID);







-- Create table
create table log_state
(
  lsid       char(10) not null,
  eid      char(10)  NOT NULL ,
  lasttime date not null,
  state    number(1) not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table log_state
  add constraint PK_LSID primary key (LSID);
alter table log_state
  add constraint FK_logstate_equipment foreign key (EID)
  references equipment (EID);
-- Create/Recreate check constraints 
alter table log_state
  add constraint CK_STATE  check (state in (0,1,2)) ; 
	--    0正常  1无故障需定期检修  2已出现故障需检修






-- Create table                                                  
create table sys_warm
(
  swid         char(10) not null,
  eid        char(10) not null,
  warm_time  date not null,
  error_type NUMBER(1)  not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table sys_warm
  add constraint PK_SWID primary key (SWID);
alter table sys_warm
  add constraint FK_syswarm_equipment foreign key (eid) references equipment(eid);
alter table sys_warm
  add constraint check_errortype
  check (error_type in (0,1,2));                   
	---0一般  1较严重   2需要立刻处理

  



-- Create table
create table manual_warm
(
  mwid        char(10) not null,
  eid       char(10) not null,
  aid       char(10) not null,
  warm_time date not null,
  notation  VARCHAR2(50)
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table manual_warm
  add constraint PK_MWID primary key (MWID);
alter table manual_warm
  add constraint FK_manualwarm_equipment foreign key (eid) references equipment(eid);
alter table manual_warm
  add constraint FK_manualwarm_admin foreign key (aid) references admin(aid);







-- Create table
create table log_session
(
  lsid   char(10) not null,
  aid  char(10) not null,
  time date not null,
  type NUMBER(1) not null
)
;
-- Create/Recreate primary, unique and foreign key constraints 
alter table log_session
  add constraint PK_ID primary key (LSID);
alter table log_session
  add constraint FK_logsession_admin  foreign key (aid) references admin(aid);
-- Create/Recreate check constraints 
alter table log_session
  add constraint check_type
  check (type in (0,1)) ;   
	 ---登录  登出
