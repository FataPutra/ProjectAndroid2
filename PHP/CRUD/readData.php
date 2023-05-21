<?php
require_once 'connection.php';

$query = "SELECT * FROM barang";
$result = mysqli_query($con, $query);
$response = array();

while ($row = mysqli_fetch_array($result)) {
    array_push($response, array(
        "kode" => $row[0],
        "nama_barang" => $row[1],
        "satuan" => $row[2],
        "jumlah" => $row[3],
        "harga" => $row[4],
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
