<?php
    error_reporting(E_ERROR | E_PARSE);
    // This line turns on logging

    // Set the file name using a variable so we only have to type it once
    $other_tables_file = "/home/student/pa4/CollegeScorecard_OtherTables.csv";

    // This reads the content of the CSV file into a variable
    $file_content = file_get_contents($other_tables_file);

    // Replace new line character with nothing
    $file_content = str_replace("\n", '', $file_content);
    //Replace carraige return with carraige return followed by a new line
    $file_content = str_replace("\r", "\r\n", $file_content);
    // Replace any instance of ' with \' so it won't bread SQL
    $file_content = str_replace("'", "\'", $file_content);
    
    //Write the new string, replacing the old csv with the new one
    file_put_contents("/home/student/pa4/CollegeScorecard_OtherTables.csv",$file_content);

?>