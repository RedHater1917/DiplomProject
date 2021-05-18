alter table EQUIPMENTRECORDS_EFFICIENCY_INDICATORS add column DATE date ^
update EQUIPMENTRECORDS_EFFICIENCY_INDICATORS set DATE = current_date where DATE is null ;
alter table EQUIPMENTRECORDS_EFFICIENCY_INDICATORS alter column DATE set not null ;
