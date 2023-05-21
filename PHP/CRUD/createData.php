<?php
require_once 'connection.php';

if ($con) {
    $kode = $_POST['kode'];
    $nama_barang = $_POST['nama_barang'];
    $satuan = $_POST['satuan'];
    $jumlah = $_POST['jumlah'];
    $harga = $_POST['harga'];

    $insert = "INSERT INTO barang(kode,nama_barang,satuan,jumlah,harga) VALUES ('$kode','$nama_barang','$satuan', '$jumlah' , '$harga')";

    if ($kode != "" && $nama_barang != "" && $satuan != "" &&  $satuan != "" && $jumlah != "" && $harga != "") {
        $result = mysqli_query($con, $insert);
        $response = array();

        if ($result) {
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
} else {
    array_push($response, array(
        'status' => 'FAILED'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
