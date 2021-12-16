<?php
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


?>