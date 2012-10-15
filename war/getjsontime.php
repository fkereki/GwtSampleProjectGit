<?php

$zone = $_REQUEST["zone"];
$callback = $_REQUEST["callback"];

try {
        date_default_timezone_set('UTC');
        $date = new DateTime();
        $date->setTimezone(new DateTimeZone($zone));
        $time = $date->format('H:i:s');
} catch (Exception $e) {
        $time = "";
}
echo $callback."({time:'".$time."'})";