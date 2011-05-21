--C:\apache-cassandra-0.7.3\bin>cassandra-cli -host localhost -port 9160 -f init-db.sql

create keyspace Hospital;
use Hospital;

create column family PatientView with
  column_type='Super' and
  comparator='BytesType' and
  subcomparator='BytesType'
;