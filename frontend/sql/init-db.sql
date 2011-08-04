--C:\apache-cassandra-0.7.3\bin>cassandra-cli -host localhost -port 9160 -f init-db.sql

create keyspace Hospital;
use Hospital;

create column family PatientAttributeValue with
  column_type='Super' and
  comparator='BytesType' and
  subcomparator='UTF8Type' and
  gc_grace=0
;

create column family Patients with
  comparator='BytesType' and
  gc_grace=0 and
  column_metadata=[
  {column_name : entityId, validation_class : BytesType, index_type : KEYS}
  ];
;

create column family Attributes with
  comparator='BytesType' and
  gc_grace=0
;

create column family TasksView with
  column_type='Super' and
  comparator='TimeUUIDType' and
  subcomparator='BytesType' and
  gc_grace=0
;