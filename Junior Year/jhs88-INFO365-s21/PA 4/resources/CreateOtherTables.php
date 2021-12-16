<?php
error_reporting(E_ERROR | E_PARSE);

$other_tables_file = "/home/student/pa4/CollegeScorecard_OtherTables.csv";

$rows = array_map('str_getcsv', file($other_tables_file));
// print_r($rows[0]); //row 1 content
// print_r($rows[1]); //row 2 content
// print $rows[1][3]; //fourth column of 2nd row

//STEP 2
$header = array_shift($rows);
$csv = array();

foreach ($rows as $row) {
	$csv[] = array_combine($header, $row);
}

print_r($csv[0]); 
print_r($csv[1]); 
print $csv[1]['LABEL']; 

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
print_r(array_slice($other_tables,0,4));


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
?>