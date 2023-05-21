<?php
require_once 'connection.php';

if ($con) {
    $kode = $_POST['kode'];
    $nama_barang = $_POST['nama_barang'];
    $satuan = $_POST['satuan'];
    $harga = $_POST['harga'];
    $jumlah = $_POST['jumlah'];
    $terjual = $_POST['terjual'];

    $insert = "INSERT INTO penjualan(kode,nama_barang,satuan,harga,jumlah,terjual) VALUES ('$kode','$nama_barang','$satuan', '$harga','$jumlah' , '$terjual')";

    $cek_kode = mysqli_num_rows(mysqli_query($con, "SELECT kode FROM penjualan WHERE kode='$kode'"));

    if ($cek_kode > 0) {
        array_push($response, array(
            'status' => 'FAILED'
        ));
    } else if ($kode != "" && $nama_barang != "" && $satuan != "" &&  $satuan != "" && $harga != "" && $jumlah != "" && $terjual != "") {
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
