Apache JAR Installation:
- Visit https://poi.apache.org/download.html and download the Apache POI .jar files
- Extract the compressed folder anywhere
- Open the project in Eclipse then right click the project name and click "Build Path > Configure Build Path"
- Click the Libraries tab
- Click Add External JARs
- Navigate to the uncompressed folder you downloaded earlier
- Select all .jar files in the top level directory and click open
- Do the same to add .jar files from the ooxml-lib and lib directories

JDBC driver JAR installation:
- Visit https://dev.mysql.com/downloads/connector/j/ then select "Platform Independent" from the dropdown menu and download the mysql connector .jar file
- Extract the compressed folder anywhere
- Open the project in Eclipse then right click the project name and click "Build Path > Configure Build Path"
- Click the Libraries tab
- Click Add External JARs
- Navigate to the uncompressed folder you downloaded earlier
- Select mysql-connector-java-<version>.jar and click open