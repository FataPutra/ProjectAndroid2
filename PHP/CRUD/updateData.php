<?php
require_once 'connection.php';

if ($con) {
    $kode = $_POST['kode'];
    $nama_barang = $_POST['nama_barang'];
    $satuan = $_POST['satuan'];
    $harga = $_POST['harga'];
    $jumlah = $_POST['jumlah'];
    $terjual = $_POST['terjual'];

    $getData = "SELECT * FROM penjualan WHERE kode = '$kode'";

    if ($kode != "" && $jumlah != "" && $terjual != "") {
        $result = mysqli_query($con, $getData);
        $rows = mysqli_num_rows($result);
        $response = array();

        if ($rows > 0) {
            $update = "UPDATE penjualan SET nama_barang = '$nama_barang' ,satuan = '$satuan' , harga = '$harga' ,jumlah = '$jumlah' , terjual = '$terjual' WHERE kode = '$kode' ";
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
