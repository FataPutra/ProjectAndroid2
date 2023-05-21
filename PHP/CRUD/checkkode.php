<?php
require_once 'connection.php';

if ($con) {
    $kode = $_POST['kode'];
    $nama_barang = $_POST['nama_barang'];
    $satuan = $_POST['satuan'];
    $harga = $_POST['harga'];
    $jumlah = $_POST['jumlah'];
    $terjual = $_POST['terjual'];

    $query = "SELECT * FROM penjualan WHERE kode = '$kode'";
    $result = mysqli_query($con, $query);
    $response = array();

    $row = mysqli_num_rows($result);

    if ($row > 0) {
        array_push($response, array(
            'status' => 'OK'
        ));
    } else {
        array_push($response, array(
            'status' => 'FAILED'
        ));
    }
} else {
    array_push($response, array(
        'status' => 'FAILED'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
