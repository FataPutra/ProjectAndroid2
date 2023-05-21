<?php
require_once 'connection.php';

if ($con) {
    $kode = $_POST['kode'];
    $nama_barang = $_POST['nama_barang'];
    $satuan = $_POST['satuan'];
    $jumlah = $_POST['jumlah'];
    $harga = $_POST['harga'];

    $getData = "SELECT * FROM barang WHERE kode = '$kode'";

    if ($kode != "" && $nama_barang != "" && $satuan != "" && $jumlah != "" && $harga != "") {
        $result = mysqli_query($con, $getData);
        $rows = mysqli_num_rows($result);
        $response = array();

        if ($rows > 0) {
            $update = "UPDATE barang SET nama_barang = '$nama_barang' ,satuan = '$satuan' ,jumlah = '$jumlah' , harga = '$harga' WHERE kode = '$kode' ";
            $exequery = mysqli_query($con, $update);

            if ($exequery) {
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
} else {
    array_push($response, array(
        'status' => 'FAILED'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
