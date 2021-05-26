if [ "$#" -ne 1 ]; then
  echo "Error! Please enter a directory!" ;
  exit 1 ;
fi

DATE=$(date '+%Y%m%d%H%M%S') ;

NEWDIRNAME=sprint$1 ;
NEWFILENAME=changelog-$DATE.yaml ;

if [ -d "$NEWDIRNAME" ]; then
echo "File $NEWFILENAME successfully created in existing directory: $NEWDIRNAME" ;
else
`mkdir -p $NEWDIRNAME`;
echo "File $NEWFILENAME successfully created after creating directory: $NEWDIRNAME" ;
fi

cd $NEWDIRNAME

echo "databaseChangeLog:
  - property:
      name: now
      value: now()
      dbms: mysql,h2
  - property:
      name: now
      value: current_timestamp
      dbms: postgresql
  - property:
      name: floatType
      value: float4
      dbms: h2,postgresql
  - property:
      name: floatType
      value: float
      dbms: mysql
  - changeSet:
      id: $DATE
      author: $USER
      comment: Enter your comment
      changes:
        - createTable:
            tableName: create_table
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    primaryKey: true
                    nullable: false
              - column:
                  name: created_by
                  type: varchar(50)
              - column:
                  name: created_date
                  type: timestamp
                  constraints:
                    nullable: false
              - column:
                  name: modified_by
                  type: varchar(50)
              - column:
                  name: modified_date
                  type: timestamp
              - column:
                  name: varchar_field
                  type: varchar(255)
              - column:
                  name: integer_field
                  type: bigint
              - column:
                  name: float_field
                  type: float
              - column:
                  name: boolean_field
                  type: boolean
              - column:
                  name: timestamp_field
                  type: timestamp
              - column:
                  name: json_field
                  type: json

        - addColumn:
            tableName: add_column
            columns:
              - column:
                  name: new_column
                  type: bigint
                  defaultValue: 0

        - dropTable:
            tableName: drop_table

        - dropColumn:
            tableName: drop_column
            columns:
              - column:
                  name: column_name
" > $NEWFILENAME ;
