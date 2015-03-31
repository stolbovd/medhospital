# Application for managing information about patients. #
<br />
**Was started to try in real life new pattern CQRS and NoSQL database Cassandra.**
<br />
<br />
**Created by Usatov Alexey.**


**The project is not completed.**
<br />

Based on CQRS architecture and Entity-Attribute-Value DB pattern.
Contains of two applications:

**Backend**
Based on spring, mysql 5, axonframework, activemq, hibernate.

**Frontend**
Based on GWT 2, cassandra 0.7.5, hector, spring, activemq, quartz, Derby DB.

Deployment is tested on tomcat 6.0.26 for both applications.

The conversation between **backend** and **frontend** is going  by JMS with guaranteed delivery.
**Frontend** send JMS with command (for example, create patient command) **backend** receives it, parses (from json) and do related modifications in MySQL. Then after successfull modifications send JMS to **frontend** and it modifies cassandra DB. **Frontend** NEVER modify cassandra DB itself - only by command from **backend** (except the automatic storing messages that were sent to **Backend**). If in the phase of modification Cassandra appears some connection problems, then the job of quartz is scheduled for request of re-sending original message from **Backend**. The period of such request can be specified.

If some commands were failed (are marked by **Backend** as failed), user can try to re-send them - they are stored in separate columnFamily of cassandra and are displayed in table on UI(functionality of re-send is not implemented yet but all possibilities are already provided).

The architecture is created with opportunity of very good horizontal scalability.

18.07.2011 Added security on frontend side.<br />
19.07.2011 Changed messageId from UUID to keyspace.clock() for good sorting.
Moved properties into properties file.

21.07.2011
Created logic that will process unsuccessfull updates of Cassandra for situations of connection problems for re-apply them when connection will be recovered.

22.07.2011 Replaced ScheduledExecutorService on Quartz with Derby DB (embedded).

27.07.2011 Improved UI for creating Attributes with only 'name' and 'shortcode' fields and linked to current buisiness logic.

28.07.2011 Improved Indexed search for simple column families, created compatible mechanims for incapsulate search parameters.

29.07.2011 Added possibility to assign simplest attribute value to patient.

30.07.2011 Added possibility of removing a patient.

04.08.2011 Added possibility of removing patient attribute value.

05.08.2011 Created skeleton for adding attribute definition (including validators) in json format.

<br />
**Next main goals**:
> - add advanced search mechanism to Cassandra dao for Super Columns. <br />