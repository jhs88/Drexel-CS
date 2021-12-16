<?php
error_reporting(E_ERROR | E_PARSE);

$other_tables_file = "/home/student/pa4/CollegeScorecard_OtherTables.csv";

$rows = array_map('str_getcsv', file($other_tables_file));

$header = array_shift($rows);
$csv = array();

foreach ($rows as $row) {
	$csv[] = array_combine($header, $row);
}

$other_tables = array();
$n=0;
foreach ($csv as $k => $v) {
	if ($v['VARIABLE NAME'] <> '') {
		$other_tables[$v['VARIABLE NAME']]['COMMENT'] = substr($v['developer-friendly name'].": ".$v['NAME OF DATA ELEMENT'],0,1024);
		$other_tables[$v['VARIABLE NAME']]['VALUES'][$n]["ID"] = $v['VALUE'];
		$other_tables[$v['VARIABLE NAME']]['VALUES'][$n]["LABEL"] = $v['LABEL'];
		$n++;
	}
}

$create_other_tables = "";
$insert_other_tables = "";

foreach ($other_tables as $table => $attributes) {
	if ($table <> '') {
		$create_other_tables = $create_other_tables."\n\nCREATE TABLE `".strtolower($table)."` (";
		$create_other_tables = $create_other_tables."\n\t`".$table."_ID` INT NOT NULL,\n\t `".$table."_VALUE` VARCHAR(1024),\n\t PRIMARY KEY (`".$table."_ID`)\n)";
		$create_other_tables = $create_other_tables." ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='".$attributes['COMMENT']."';";

		//Generate Inserts:
		$insert_other_tables = $insert_other_tables."\n INSERT INTO `".strtolower($table)."` (`".$table."_ID`,`".$table."_VALUE`) VALUES ";
		foreach ($attributes['VALUES'] as $k => $v) {
			if (is_numeric($v["ID"])) {
				$insert_other_tables = $insert_other_tables."\n\t(".$v["ID"].", '".str_replace("'","",$v["LABEL"])."'),";
			}
		}
		$insert_other_tables = rtrim($insert_other_tables,',').";";
	}
}

file_put_contents('/home/student/pa4/create_other_tables.sql',$create_other_tables);
file_put_contents('/home/student/pa4/insert_other_tables.sql',$insert_other_tables);

//
// Create the main tables from catagoties:
//

$csv_file = "/home/student/pa4/CollegeScorecard_DataDictionary.csv";
// Creates an associative array from a CSV... found this somewhere on the Internet...
$rows = array_map('str_getcsv', file($csv_file));
$header = array_shift($rows);
$csv = array();
foreach ($rows as $row) {
	$csv[] = array_combine($header, $row);
}

$tables = array();

foreach ($csv as $k => $v) {
	$tables[$v['dev-category']][$v['VARIABLE NAME']]['DATA_TYPE'] = $v['API data type'];
	$tables[$v['dev-category']][$v['VARIABLE NAME']]['COMMENT'] = $v['﻿NAME OF DATA ELEMENT'];
}

$create_schema = "";

// Data types mapping:
$integer = "INT";
$float = "FLOAT(20,10)";

foreach ($tables as $table => $columns) {
	if ($table <> '' && $table <> 'completion') {
		$create_schema = $create_schema."\n\n CREATE TABLE `".$table."` (\n";
		
		// Create primary key:
		$create_schema = $create_schema."\n\t`".$table."_ID` INT NOT NULL AUTO_INCREMENT,";
		if ($table <> 'root') {
			$create_schema = $create_schema."\n\t`OPEID` INT NOT NULL REFERENCES root(`OPEID`),";
		}
		
		$cols = 0;
		foreach ($columns as $column => $attributes) {	
			if ($column <> '') {
				//Set data type:
				if ($attributes['DATA_TYPE'] == 'integer') {
					$datatype=$integer;
				} elseif ($attributes['DATA_TYPE'] == 'float') {
					$datatype=$float;
				} else {
					$datatype="VARCHAR(2048)";
				}
				
				//Check if this column is a foriegn key:
				if (isset($other_tables[$column])) {
					//There is a foreign key
					$create_schema = $create_schema."\n\t`".$column."` ".$datatype." COMMENT '".substr(str_replace("'","",$attributes['COMMENT']),0,1024)."' REFERENCES `".strtolower($column)."` (`".$column."_ID`),";
				} else {
					$create_schema = $create_schema."\n\t`".$column."` ".$datatype." COMMENT '".substr(str_replace("'","",$attributes['COMMENT']),0,1024)."',";
				}
				
			}
			$cols++;
		}
		$create_schema = $create_schema."\n\t`Report_Year` INT COMMENT 'Year data reported',";
		$create_schema = $create_schema."\n\tPRIMARY KEY (`".$table."_ID`)\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		
		echo "\nTable ".$table." has ".$cols." columns.";
	}
}

file_put_contents('/home/student/pa4/create_tables.sql',$create_schema);


//
// Insert everything:
//

$servername = "localhost";
$username = "jhs88";
$password = "password";
$dbname = "CollegeScorecard";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
echo "Connected successfully";

$csv_file = "/home/student/pa4/MERGED2016_17_PP.csv";
$csv_handle = fopen($csv_file, "r");

$inserted_count=0;
$failed_count=0;

if ($csv_handle) {
	$n=0;
	while (($line = fgets($csv_handle)) !== false) {
		if ($n > 9999999) {break;}
		if ($n==0) {
			$csv_header = explode(',',$line);
		} else {
			$csv_columns = explode(',',$line);
			// Make associated array:
			$csv_row = array();
			for ($c=1;$c < count($csv_header);$c++) {
				$csv_row[trim($csv_header[$c])] = trim($csv_columns[$c]);
			}
			foreach ($tables as $table => $columns) {
				if (trim($table) <> '' && trim($table) <> 'completion') {
					$inserts = "\n\nINSERT INTO `".$table."`";
					// Column/Value List:
					$columns_list = "";
					$values_list = "";
					foreach ($columns as $column => $attributes) {
						if (trim($column) <> '' && trim($column) <> 'OPEID') { 
							$columns_list = $columns_list."`".$column."`,";
							if ($csv_row[$column] == 'NULL' || $csv_row[$column] == 'PrivacySuppressed' || $csv_row[$column] == '') {
								$values_list = $values_list."NULL,";
							} elseif ($attributes["DATA_TYPE"] == 'integer' || $attributes["DATA_TYPE"] == 'float') {
								if (is_numeric($csv_row[$column])) {
									$values_list = $values_list.$csv_row[$column].",";
								} else {
									$values_list = $values_list."NULL,";
								}
							} else {
								$values_list = $values_list."'".mysqli_real_escape_string($conn,$csv_row[$column])."',";
							}
						}
					}
					$columns_list = $columns_list." `OPEID`";
					$values_list = $values_list.$csv_row['OPEID'];
					$inserts = "\n\nINSERT INTO `".$table."` (".$columns_list.") VALUES (".$values_list.");";
					if ($conn->query($inserts) === TRUE) {
						$inserted_count++;
					} else {
						echo "\nOPEID".$csv_row['OPEID']." FAILED for table ".$table."\n\n\t" . $conn->error;
						echo "\n\n".$inserts;
						$failed_count++;
					}
				}
			}
		}
		$n++;
    }
    fclose($csv_handle);
	echo "\n\nSuccessfully Inserted: ".$inserted_count;
	echo "\n\nFailed Inserted: ".$failed_count;
} else {
    echo "\n\nERROR!";
} 

?>